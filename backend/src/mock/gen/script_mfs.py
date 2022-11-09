import sys
import csv
import pandas as pd
import random
import re

#argv params:
    #1. path to file containing code and company name
    #2. exchange name

mf_data = ['set define off;']
MAX_RECORDS_TO_GENERATE = int(sys.argv[3]);

def trimStr(s):
    m = re.search(r";", s)
    if m:
        return s[0:m.start()].strip()
    else:
        return s
    
    
with open(sys.argv[1], 'r') as f:
    df = pd.read_csv(f, sep='\t')
    df['Symbol'] = df['Symbol'].astype(str)
    print(df)
    mfTypes = ['stock_funds', 'bond_funds', 'index_funds', 'balanced_funds', 'money_market_funds', 'income_funds']
    
    for i, d in df.iterrows():
        if i >= MAX_RECORDS_TO_GENERATE:
            break
            
        if not("'" in str(d['Name'])):
            mf_data.append("INSERT INTO SC_MUTUAL_FUNDS(CODE, NAME, QUANTITY, CURRENT_PRICE, INTEREST_RATE, MF_TYPE) VALUES ('" + d['Symbol'] + "', '" + trimStr(d['Name']) + "', " + str(random.randint(7400, 3020500)) + ', ' + str(random.randint(1000, 100000)) + ', ' + str(round(random.uniform(1.0, 4.0), 2)	) + ", '" + random.choice(mfTypes) + "');")
    
#save SQL queries into mf_sql_<exchangename>.txt
with open(r"mf_sql_" + sys.argv[2] + ".txt","w") as f:
    f.write('\n'.join(mf_data))
