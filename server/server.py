from flask import Flask
from flask import render_template
from flask import request
from bson.json_util import dumps
from bson.json_util import loads

from pymongo import MongoClient
db_client = MongoClient("mduan.com", 27017)
db = db_client["socialpub"]

app = Flask(__name__)

def bookTableLookup(bookid):
    return db["books"].find_one({"bookid":bookid})

@app.route("/getcomments", methods=["GET"])
def getcomments():
    bookid = int(request.args.get("bookid", 1))
    paragraphStartIndex = int(request.args.get("paragraphStartIndex", None))
    elementStartIndex = int(request.args.get("elementStartIndex", None))
    charStartIndex = int(request.args.get("charStartIndex", None))
    paragraphEndIndex = int(request.args.get("paragraphEndIndex", None))
    elementEndIndex = int(request.args.get("elementEndIndex", None))
    charEndIndex = int(request.args.get("charEndIndex", None))
    if not paragraphStartIndex or not elementStartIndex or not charStartIndex:
        return dumps({"error": "incorrect starting page indexing values"})
    if not paragraphEndIndex or not elementEndIndex or not charEndIndex:
        return dumps({"error": "incorrect ending page indexing values"})

    collection = bookTableLookup(bookid)
    if not collection:
        return dumps({"error": "no such book"})
    collection = collection["collection"]

    query = {
        "$and" : [{"paragraphStartIndex": {"$lte": paragraphEndIndex}},
                {"paragraphEndIndex": {"$gte": paragraphStartIndex}}],
        "$and" : [{"elementStartIndex": {"$lte": elementEndIndex}},
                {"elementEndIndex": {"$gte": elementStartIndex}}],
        "$and" : [{"charStartIndex": {"$lte": charEndIndex}},
                {"charEndIndex": {"$gte": charStartIndex}}]                 
    }
    results = [x for x in db[collection].find(query)]
    return dumps(results)


@app.route("/comment", methods=["POST"])
def comment():
    db["testbook"].insert(loads(request.data))
    return ""

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")
