import pyrebase
import csv
import requests

config = {
    "apiKey": "AIzaSyB3nDviElVGYksOkRl0BCUw2C386eGsraw",
    "authDomain": "lifesaver-b170d.firebaseapp.com",
    "databaseURL": "https://lifesaver-b170d.firebaseio.com",
    "storageBucket": "lifesaver-b170d.appspot.com",
}

firebase = pyrebase.initialize_app(config)

db = firebase.database()

with open('Hospital Database.csv', 'r') as file:
    reader = csv.reader(file)
    reader = list(reader)

    reader = reader[1:]



    for hospital in reader:

        
        data = {
            
            'Name': hospital[0],
            'Type': 1,
            'Address': '',
            'Vacant': hospital[4],
            'Total': hospital[2],
            'Occupied': hospital[3],
            'Contact': [123456789],
            'Locality': 'Delhi',
            'Last time of update': '27-5-2052'
        }

        hospitalRef = db.child('Hospitals')
        key = hospitalRef.push(data)['name']

        URL = "https://maps.googleapis.com/maps/api/geocode/json"

        location = hospital[0]
        PARAMS = {'address': location, 'key': 'YOUR_KEY_HERE'}

        r = requests.get(url=URL, params=PARAMS)
        data = r.json()
        if(data['results']):
            latitude = data['results'][0]['geometry']['location']['lat']
            longitude = data['results'][0]['geometry']['location']['lng']

            data = {'Latitude': latitude, 'Longitude': longitude}
            ref = db.child('Location').child(key)
            ref.set(data)


        
        ref = db.child('Hospitals').child(key)
        ref.set(data)

        data = {'Name': hospital[0], 'City': 'Delhi'}

        ref = db.child('Private').child(key)
        ref.set(data)

        
        ref = db.child('Names').child(hospital[0])
        ref.set(key)

 
