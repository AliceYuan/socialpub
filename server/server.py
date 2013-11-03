from flask import Flask
from flask import render_template
from flask import request
from bson.json_util import dumps
from bson.json_util import loads

from pymongo import MongoClient
db_client = MongoClient("mduan.com", 27017)
db = db_client["socialpub"]

app = Flask(__name__)

def bookTableLookup(bookId):
    return db["books"].find_one({"bookid":bookId})

@app.route("/getcomments", methods=["GET"])
def getcomments():
    bookId = int(request.args.get("bookId", 1))
    paragraphStartIndex = int(request.args.get("paragraphStartIndex", None))
    paragraphEndIndex = int(request.args.get("paragraphEndIndex", None))
    elementStartIndex = int(request.args.get("elementStartIndex", None))
    elementEndIndex = int(request.args.get("elementEndIndex", None))
    charStartIndex = int(request.args.get("charStartIndex", None))
    charEndIndex = int(request.args.get("charEndIndex", None))
    if not paragraphStartIndex or not elementStartIndex or not charStartIndex:
        return dumps({"error": "incorrect starting page indexing values"})
    if not paragraphEndIndex or not elementEndIndex or not charEndIndex:
        return dumps({"error": "incorrect ending page indexing values"})

    collection = bookTableLookup(bookId)
    if not collection:
        return dumps({"error": "no such book"})
    collection = collection["collection"]

    query = {
        "paragraphStartIndex": {"$lte": paragraphEndIndex},
        "paragraphEndIndex": {"$gte": paragraphStartIndex},
        "elementStartIndex": {"$lte": elementEndIndex},
        "elementEndIndex": {"$gte": elementStartIndex},
        "charStartIndex": {"$lte": charEndIndex},
        "charEndIndex": {"$gte": charStartIndex}
    }
    results = [x for x in db[collection].find(query)]

    return dumps(results)


@app.route("/comment", methods=["POST"])
def comment():
    db["testbook"].insert(loads(request.data))
    return ""

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")
