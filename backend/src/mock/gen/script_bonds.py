import sys
import csv
import pandas as pd
import random
import re

#argv params:
    #1. path to file containing code and company name
    #2. exchange name
    #3. max number of records to gen

bond_data = ['set define off;']
joinDf = []
bondTypes = []
MAX_RECORDS_TO_GENERATE = int(sys.argv[3]);

def trimStr(s):
    m = re.search(r"\d", s)
    if m:
        return s[0:m.start()].strip()
    else:
        return s


with open(sys.argv[1], 'r') as f:
    df = pd.read_csv(f)
    df['Symbol'] = df['Symbol'].astype(str)

    bt = df['Issue_type'].unique()
    bondTypes = [b.replace(' ', '_').lower() for b in bt]

    btMap = {bt[i] : bondTypes[i] for i in range(0, len(bondTypes)) }

    for i, d in df.iterrows():
        if i >= MAX_RECORDS_TO_GENERATE:
            break

        if not("'" in str(d['Name'])):
            bond_data.append("INSERT INTO SC_BONDS(CODE, NAME, QUANTITY, CURRENT_PRICE, INTEREST_RATE, DURATION_MONTHS, BOND_TYPE, EXCHANGE_NAME) VALUES ('" + d['Symbol'] + "', '" + trimStr(d['Name']) + "', " + str(random.randint(7400, 3020500)) + ', ' + str(random.randint(1000, 100000)) + ', ' + str(d['Interest_rate']) + ', ' + str(random.randint(60, 240)) + ", '" + btMap[d['Issue_type']] + "', '" + sys.argv[2] + "');")

#save SQL queries into bonds_sql_<exchangename>.txt
with open(r"bonds_sql_" + sys.argv[2] + ".txt","w") as f:
    f.write('\n'.join(bond_data))
