from flask import Flask
from flask import render_template
from flask import request
from bson.json_util import dumps
from bson.json_util import loads

from pymongo import MongoClient
db_client = MongoClient("mduan.com", 27017)
db = db_client["socialpub"]

app = Flask(__name__)

def booktableLookup(bookid):
    return db["books"].find_one({"bookid":bookid})

@app.route("/getcomments", methods=["GET"])
def getcomments():
    bookid = request.args.get("bookid", "")
    userid = request.args.get("userid", "")
    print "getting comment for user " + userid

    results = [x for x in db["testbook"].find({"userid": int(userid)})]
    return dumps(results)


@app.route("/comment", methods=["POST"])
def comment():
    db["testbook"].insert(loads(request.data))
    return ""

if __name__ == "__main__":
    app.run(debug=True)
