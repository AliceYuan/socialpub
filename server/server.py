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
    page = int(request.args.get("page", 0))
    collection = bookTableLookup(bookid)
    if not collection:
        return dumps({"error": "no such book"})
    collection = collection["collection"]

    results = [x for x in db[collection].find({"page":page})]
    return dumps(results)


@app.route("/comment", methods=["POST"])
def comment():
    db["testbook"].insert(loads(request.data))
    return ""

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")
