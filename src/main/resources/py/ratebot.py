import pandas
import json

content = pandas.read_html("https://rate.bot.com.tw/xrt?Lang=zh-TW")
curList = content[0]
curList = curList.iloc[:, 0:5]
curList.columns = ["curCode", "cashBuy", "cashSell", "spotBuy", "spotSell"]
curList["curCode"] = curList["curCode"].str.extract("\((\w+)\)")
curList["cashBuy"] = pandas.to_numeric(curList["cashBuy"], errors='coerce')
curList["cashSell"] = pandas.to_numeric(curList["cashSell"], errors='coerce')
curList["spotBuy"] = pandas.to_numeric(curList["spotBuy"], errors='coerce')
curList["spotSell"] = pandas.to_numeric(curList["spotSell"], errors='coerce')
jsonStr = curList.to_json(orient="records")
print(json.dumps(json.loads(jsonStr)))
