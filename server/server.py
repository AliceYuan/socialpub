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

def getIndexStr(paragraphIndex, elementIndex, charIndex):
    NUM_PADDING = 10
    str_ = '%s:%s:%s' % (
        str(paragraphIndex).zfill(NUM_PADDING),
        str(elementIndex).zfill(NUM_PADDING),
        str(charIndex).zfill(NUM_PADDING),
    )
    return str_

@app.route("/getcomments", methods=["GET"])
def getcomments():
    bookId = request.args.get("bookId", "")
    paragraphStartIndex = int(request.args.get("paragraphStartIndex", None))
    paragraphEndIndex = int(request.args.get("paragraphEndIndex", None))
    elementStartIndex = int(request.args.get("elementStartIndex", None))
    elementEndIndex = int(request.args.get("elementEndIndex", None))
    charStartIndex = int(request.args.get("charStartIndex", None))
    charEndIndex = int(request.args.get("charEndIndex", None))
    if paragraphStartIndex is None or elementStartIndex is None or charStartIndex is None:
        return dumps({"error": "incorrect starting page indexing values"})
    if paragraphEndIndex is None or elementEndIndex is None or charEndIndex is None:
        return dumps({"error": "incorrect ending page indexing values"})

    idxStartStr = getIndexStr(paragraphStartIndex, elementStartIndex, charStartIndex)
    idxEndStr = getIndexStr(paragraphStartIndex, elementStartIndex, charEndIndex)

    #collection = bookTableLookup(bookId)
    #if not collection:
    #    return dumps({"error": "no such book"})
    #collection = collection["collection"]

    results = []
    for row in db['comments'].find({'bookId': bookId}):
        pageStartStr = getIndexStr(row['paragraphStartIndex'], row['elementStartIndex'], row['charStartIndex'])
        pageEndStr = getIndexStr(row['paragraphEndIndex'], row['elementEndIndex'], row['charEndIndex'])
        if ((idxEndStr >= pageStartStr and idxEndStr < pageEndStr) or
            (idxStartStr >= pageStartStr and idxStartStr < pageEndStr)):
            results.append(row)

    #query = {
    #    "elementStartIndex": {"$lt": elementEndIndex},
    #    "elementEndIndex": {"$gt": elementStartIndex}
    #}
    #results = [x for x in db[collection].find(query)]

    return dumps(results)


@app.route("/comment", methods=["POST"])
def comment():
    db["comments"].insert(loads(request.data))
    return ""

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")
