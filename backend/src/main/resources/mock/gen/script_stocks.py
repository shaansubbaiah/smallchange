import sys
import csv
import pandas as pd
import random as rand

#argv params:
    #1. path to file containing code and company name
    #2. path to file containing code, volume and closing price
    #3. exchange name
    #4. max number of records to gen

stock_data = ['set define off;']
joinDf = []
stockTypes = ['main_index_stocks', 'small_cap_company_stocks', 'international_market_stocks']
MAX_RECORDS_TO_GENERATE = int(sys.argv[4]);

with open(sys.argv[1], 'r') as f:
    with open(sys.argv[2], 'r') as f1:
        reader = csv.reader(f)
        codeDf = pd.DataFrame({'Symbol': pd.Series(dtype='str'),
                       'Name': pd.Series(dtype='str')})

        metricsDf = pd.read_csv(f1)
        metricsDf['Symbol'] = metricsDf['Symbol'].astype(str)

        for row in reader:
            sd = row[0].split('\t')
            codeDf.loc[len(codeDf)] = sd

        codeDf['Symbol'] = codeDf['Symbol'].astype(str)

        print(codeDf.info(), metricsDf.info())
        joinDf = codeDf.merge(metricsDf, on='Symbol', how='inner')
        print(joinDf.head(), joinDf.describe(), joinDf.info())

    for i, d in joinDf.iterrows():
        if i >= MAX_RECORDS_TO_GENERATE:
            break
        if not("'" in str(d['Name'])):
            stock_data.append("INSERT INTO SC_STOCKS(CODE, NAME, QUANTITY, CURRENT_PRICE, STOCK_TYPE, EXCHANGE_NAME) VALUES ('" + d['Symbol'] + "', '" + d['Name'] + "',  " + str(d['Volume']) + ', ' + str(d['High']) + ", '" + rand.choice(stockTypes) + "', '" + sys.argv[3] + "');")

#save SQL queries into stocks_sql_<exchangename>.txt
with open(r"stocks_sql_" + sys.argv[3] + ".txt","w") as f:
    f.write('\n'.join(stock_data))
