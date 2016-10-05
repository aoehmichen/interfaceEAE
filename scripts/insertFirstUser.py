import sys
from pymongo import MongoClient
from Crypto.Hash import SHA256

#####################################
# main program                      #
#####################################

mongoURL = sys.argv[1]
mongoEAEPassword = sys.argv[2]
adminPwd = sys.argv[3]

hash = SHA256.new()
hash.update("eae")

db = MongoClient('mongodb://' + mongoURL + '/').eae
db.authenticate('eae', mongoEAEPassword, mechanism='SCRAM-SHA-1')

adminUser = {"username": "admin",
             "password": hash.hexdigest()
             }

db.users.insert_one(adminUser)
