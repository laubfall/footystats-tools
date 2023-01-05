
/*
db.createUser( { user: "admin",
	pwd: "admin",
	roles: [ "userAdminAnyDatabase",
		"dbAdminAnyDatabase",
		"readWriteAnyDatabase"] } )

*/
db = db.getSiblingDB('footystats');
db.createUser(
    {
        user: 'footystats_user',
        pwd: '1234',
        roles: [{ role: 'readWrite', db: 'footystats' }]
    }
);
