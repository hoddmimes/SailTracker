
/*
* Copyright (c)  Hoddmimes Solution AB 2021.
*
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
            
    package com.hoddmimes.sailtracker.generated;


    import com.mongodb.BasicDBObject;
    import com.mongodb.MongoClient;
    import com.mongodb.client.MongoCollection;
    import com.mongodb.client.MongoDatabase;
    import com.mongodb.client.MongoIterable;
    import com.mongodb.client.model.CreateCollectionOptions;
    import com.mongodb.client.model.ValidationOptions;
    import org.bson.conversions.Bson;
    import com.mongodb.client.model.UpdateOptions;
    import com.mongodb.client.FindIterable;
    import com.mongodb.client.MongoCursor;
    import com.mongodb.client.model.IndexOptions;


    import org.bson.BsonType;
    import org.bson.Document;
    import org.bson.types.ObjectId;

    import com.mongodb.client.model.Filters;
    import com.mongodb.client.result.DeleteResult;
    import com.mongodb.client.result.UpdateResult;

    import java.util.List;
    import java.util.Date;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.stream.Collectors;
    import com.hoddmimes.jsontransform.DateUtils;

    
    import com.hoddmimes.sailtracker.generated.dbobjects.*;
    
    public class MongoAux {
            private String mDbName;
            private String mDbHost;
            private int mDbPort;

            private MongoClient mClient;
            private MongoDatabase mDb;
            
           private MongoCollection mUserCollection;
           private MongoCollection mPositionCollection;

            public MongoAux( String pDbName, String pDbHost, int pDbPort ) {
               mDbName = pDbName;
               mDbHost = pDbHost;
               mDbPort = pDbPort;
            }

            public void connectToDatabase() {
               try {
                 mClient = new MongoClient( mDbHost, mDbPort);
                 mDb = mClient.getDatabase( mDbName );
            
                 mUserCollection = mDb.getCollection("User");
              
                 mPositionCollection = mDb.getCollection("Position");
              
               }
               catch(Exception e ) {
                  e.printStackTrace();
               }
            }

            public void  dropDatabase()
            {
                MongoClient tClient = new MongoClient(mDbHost, mDbPort);
                MongoDatabase tDb = tClient.getDatabase( mDbName );


                try {
                    tDb.drop();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                finally {
                   if (mClient != null) {
                        mClient.close();
                   }
                   mDb = null;
            
                    mUserCollection = null;
                
                    mPositionCollection = null;
                
                 }
            }

            private void close() {
                if (mClient != null) {
                    mClient.close();
                    mDb = null;

            
                    mUserCollection = null;
                
                    mPositionCollection = null;
                
                }
            }




            private void createCollection(String pCollectionName, List<DbKey> pKeys, Bson pValidator )
            {
                MongoClient tClient = new MongoClient(mDbHost, mDbPort);
                MongoDatabase db = tClient.getDatabase( mDbName );

                //ValidationOptions validationOptions = new ValidationOptions().validator(pValidator);
                //CreateCollectionOptions tOptions = new CreateCollectionOptions().validationOptions(validationOptions);
                //db.createCollection(pCollectionName, tOptions );
                db.createCollection(pCollectionName);

                MongoCollection tCollection = db.getCollection( pCollectionName );
                if (pKeys != null) {
                  for ( DbKey dbk : pKeys ) {
                       tCollection.createIndex(new BasicDBObject(dbk.mKeyName, 1), new IndexOptions().unique(dbk.mUnique));
                  }
                }
                tClient.close();
            }


            private boolean collectionExit( String pCollectionName, MongoIterable<String> pCollectionNameNames ) {
              MongoCursor<String> tItr = pCollectionNameNames.iterator();
              while( tItr.hasNext()) {
                String tName = tItr.next();
                if (tName.compareTo( pCollectionName ) == 0) {
                  return true;
                }
              }
              return false;
            }


            public void createDatabase( boolean pReset ) {
                this.close();
                MongoClient mClient = new MongoClient(mDbHost, mDbPort);
                MongoDatabase tDB = mClient.getDatabase( mDbName );
                MongoIterable<String> tCollectionNames = tDB.listCollectionNames();

                
                       if ((pReset) || (!collectionExit("User", tCollectionNames ))) {
                          createUserCollection();
                       }
                   
                       if ((pReset) || (!collectionExit("Position", tCollectionNames ))) {
                          createPositionCollection();
                       }
                   
            }


            
        private void createUserCollection() {
            ArrayList<DbKey> tKeys = new ArrayList<>();
            
                    tKeys.add( new DbKey("MMSI", true));

            createCollection("User", tKeys, null );
        }
    
        private void createPositionCollection() {
            ArrayList<DbKey> tKeys = new ArrayList<>();
            
                tKeys.add( new DbKey("MMSI", false));

            createCollection("Position", tKeys, null );
        }
    
                    public MongoCollection getUserCollection() {
                      return mUserCollection;
                    }
                
                    public MongoCollection getPositionCollection() {
                      return mPositionCollection;
                    }
                
        /**
        * CRUD DELETE methods
        */
        public long deleteUser( Bson pFilter) {
           DeleteResult tResult = mUserCollection.deleteMany( pFilter );
           return tResult.getDeletedCount();
        }

        public long deleteUserByMongoId( String pMongoObjectId) {
            Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));
            DeleteResult tResult = mUserCollection.deleteOne( tFilter );
            return tResult.getDeletedCount();
        }

        
            public long deleteUserByMMSI( String pMMSI ) {
                
            Bson tKeyFilter= Filters.eq("MMSI", pMMSI); 
                DeleteResult tResult =  mUserCollection.deleteMany(tKeyFilter);
                return tResult.getDeletedCount();
            }
        
        /**
        * CRUD INSERT methods
        */
        public void insertUser( User pUser ) {
            Document tDoc = pUser.getMongoDocument();
            mUserCollection.insertOne( tDoc );
            ObjectId _tId = tDoc.getObjectId("_id");
            if (_tId != null) {
                pUser.setMongoId( _tId.toString());
            }
        }

        public void insertUser( List<User> pUserList ) {
           List<Document> tList = pUserList.stream().map( User::getMongoDocument).collect(Collectors.toList());
           mUserCollection.insertMany( tList );
           for( int i = 0; i < tList.size(); i++ ) {
             ObjectId _tId = tList.get(i).getObjectId("_id");
             if (_tId != null) {
                pUserList.get(i).setMongoId( _tId.toString());
             }
           }
        }

    
        /**
        * CRUD UPDATE (INSERT) methods
        */
        public UpdateResult updateUserByMongoId( String pMongoObjectId, User pUser ) {
            Bson tFilter =  Filters.eq("_id", new ObjectId(pMongoObjectId));
            Document tDocSet = new Document("$set", pUser.getMongoDocument());
            UpdateResult tUpdSts = mUserCollection.updateOne( tFilter, tDocSet, new UpdateOptions());
            return tUpdSts;
        }

        public UpdateResult updateUser( User pUser, boolean pUpdateAllowInsert ) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
        
        Bson tKeyFilter= Filters.and( 
            Filters.eq("MMSI", pUser.getMMSI().get()) );
    


        Document tDocSet = new Document("$set", pUser.getMongoDocument());

        UpdateResult tUpdSts = mUserCollection.updateOne( tKeyFilter, tDocSet, tOptions);
        return tUpdSts;
        }


        public UpdateResult updateUser( String pMMSI, User pUser, boolean pUpdateAllowInsert ) {
          UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
          
        Bson tKeyFilter= Filters.and( 
            Filters.eq("MMSI", pMMSI) );
    

           Document tDocSet = new Document("$set", pUser.getMongoDocument());

           UpdateResult tUpdSts = mUserCollection.updateOne( tKeyFilter, tDocSet, tOptions);
           return tUpdSts;
        }

        public UpdateResult updateUser( Bson pFilter, User pUser, boolean pUpdateAllowInsert ) {
           UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
           Document tDocSet = new Document("$set", pUser.getMongoDocument());
           UpdateResult tUpdSts = mUserCollection.updateOne( pFilter, tDocSet, tOptions);
           return tUpdSts;
        }
    
        /**
        * CRUD FIND methods
        */

        public List<User> findUser( Bson pFilter  ) {
         return findUser( pFilter, null );
        }

        public List<User> findUser( Bson pFilter, Bson pSortDoc  ) {

        FindIterable<Document> tDocuments = (pSortDoc == null) ? this.mUserCollection.find( pFilter ) :
        this.mUserCollection.find( pFilter ).sort( pSortDoc );


        if (tDocuments == null) {
        return null;
        }

        List<User> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
        Document tDoc = tIter.next();
        User tUser = new User();
        tUser.decodeMongoDocument( tDoc );
        tResult.add( tUser );
        }
        return tResult;
        }



        public List<User> findAllUser()
        {
           List<User> tResult = new ArrayList<>();

           FindIterable<Document> tDocuments  = this.mUserCollection.find();
           MongoCursor<Document> tIter = tDocuments.iterator();
           while( tIter.hasNext()) {
               Document tDoc = tIter.next();
               User tUser = new User();
               tUser.decodeMongoDocument( tDoc );
               tResult.add(tUser);
            }
            return tResult;
        }

        public User findUserByMongoId( String pMongoObjectId ) {
        Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));

        FindIterable<Document> tDocuments = this.mUserCollection.find( tFilter );
        if (tDocuments == null) {
            return null;
        }

        List<User> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
        Document tDoc = tIter.next();
        User tUser = new User();
        tUser.decodeMongoDocument( tDoc );
        tResult.add( tUser );
        }
        return (tResult.size() > 0) ? tResult.get(0) : null;
        }


        public List<User> findUser( String pMMSI ) {
            
        Bson tKeyFilter= Filters.and( 
            Filters.eq("MMSI", pMMSI) );
    

        FindIterable<Document> tDocuments = this.mUserCollection.find( tKeyFilter );
        if (tDocuments == null) {
           return null;
        }

        List<User> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
           Document tDoc = tIter.next();
           User tUser = new User();
           tUser.decodeMongoDocument( tDoc );
           tResult.add( tUser );
        }
        return tResult;
        }

        
            public List<User> findUserByMMSI( String pMMSI ) {
            List<User> tResult = new ArrayList<>();
            
            Bson tKeyFilter= Filters.eq("MMSI", pMMSI); 

            FindIterable<Document> tDocuments  = this.mUserCollection.find( tKeyFilter );
            MongoCursor<Document> tIter = tDocuments.iterator();
            while( tIter.hasNext()) {
            Document tDoc = tIter.next();
            User tUser = new User();
            tUser.decodeMongoDocument( tDoc );
            tResult.add(tUser);
            }
            return tResult;
            }
        
        /**
        * CRUD DELETE methods
        */
        public long deletePosition( Bson pFilter) {
           DeleteResult tResult = mPositionCollection.deleteMany( pFilter );
           return tResult.getDeletedCount();
        }

        public long deletePositionByMongoId( String pMongoObjectId) {
            Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));
            DeleteResult tResult = mPositionCollection.deleteOne( tFilter );
            return tResult.getDeletedCount();
        }

        
            public long deletePositionByMMSI( String pMMSI ) {
                
            Bson tKeyFilter= Filters.eq("MMSI", pMMSI); 
                DeleteResult tResult =  mPositionCollection.deleteMany(tKeyFilter);
                return tResult.getDeletedCount();
            }
        
        /**
        * CRUD INSERT methods
        */
        public void insertPosition( Position pPosition ) {
            Document tDoc = pPosition.getMongoDocument();
            mPositionCollection.insertOne( tDoc );
            ObjectId _tId = tDoc.getObjectId("_id");
            if (_tId != null) {
                pPosition.setMongoId( _tId.toString());
            }
        }

        public void insertPosition( List<Position> pPositionList ) {
           List<Document> tList = pPositionList.stream().map( Position::getMongoDocument).collect(Collectors.toList());
           mPositionCollection.insertMany( tList );
           for( int i = 0; i < tList.size(); i++ ) {
             ObjectId _tId = tList.get(i).getObjectId("_id");
             if (_tId != null) {
                pPositionList.get(i).setMongoId( _tId.toString());
             }
           }
        }

    
        /**
        * CRUD UPDATE (INSERT) methods
        */
        public UpdateResult updatePositionByMongoId( String pMongoObjectId, Position pPosition ) {
            Bson tFilter =  Filters.eq("_id", new ObjectId(pMongoObjectId));
            Document tDocSet = new Document("$set", pPosition.getMongoDocument());
            UpdateResult tUpdSts = mPositionCollection.updateOne( tFilter, tDocSet, new UpdateOptions());
            return tUpdSts;
        }

        public UpdateResult updatePosition( Position pPosition, boolean pUpdateAllowInsert ) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
        
        Bson tKeyFilter= Filters.and( 
            Filters.eq("MMSI", pPosition.getMMSI().get()) );
    


        Document tDocSet = new Document("$set", pPosition.getMongoDocument());

        UpdateResult tUpdSts = mPositionCollection.updateOne( tKeyFilter, tDocSet, tOptions);
        return tUpdSts;
        }


        public UpdateResult updatePosition( String pMMSI, Position pPosition, boolean pUpdateAllowInsert ) {
          UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
          
        Bson tKeyFilter= Filters.and( 
            Filters.eq("MMSI", pMMSI) );
    

           Document tDocSet = new Document("$set", pPosition.getMongoDocument());

           UpdateResult tUpdSts = mPositionCollection.updateOne( tKeyFilter, tDocSet, tOptions);
           return tUpdSts;
        }

        public UpdateResult updatePosition( Bson pFilter, Position pPosition, boolean pUpdateAllowInsert ) {
           UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
           Document tDocSet = new Document("$set", pPosition.getMongoDocument());
           UpdateResult tUpdSts = mPositionCollection.updateOne( pFilter, tDocSet, tOptions);
           return tUpdSts;
        }
    
        /**
        * CRUD FIND methods
        */

        public List<Position> findPosition( Bson pFilter  ) {
         return findPosition( pFilter, null );
        }

        public List<Position> findPosition( Bson pFilter, Bson pSortDoc  ) {

        FindIterable<Document> tDocuments = (pSortDoc == null) ? this.mPositionCollection.find( pFilter ) :
        this.mPositionCollection.find( pFilter ).sort( pSortDoc );


        if (tDocuments == null) {
        return null;
        }

        List<Position> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
        Document tDoc = tIter.next();
        Position tPosition = new Position();
        tPosition.decodeMongoDocument( tDoc );
        tResult.add( tPosition );
        }
        return tResult;
        }



        public List<Position> findAllPosition()
        {
           List<Position> tResult = new ArrayList<>();

           FindIterable<Document> tDocuments  = this.mPositionCollection.find();
           MongoCursor<Document> tIter = tDocuments.iterator();
           while( tIter.hasNext()) {
               Document tDoc = tIter.next();
               Position tPosition = new Position();
               tPosition.decodeMongoDocument( tDoc );
               tResult.add(tPosition);
            }
            return tResult;
        }

        public Position findPositionByMongoId( String pMongoObjectId ) {
        Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));

        FindIterable<Document> tDocuments = this.mPositionCollection.find( tFilter );
        if (tDocuments == null) {
            return null;
        }

        List<Position> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
        Document tDoc = tIter.next();
        Position tPosition = new Position();
        tPosition.decodeMongoDocument( tDoc );
        tResult.add( tPosition );
        }
        return (tResult.size() > 0) ? tResult.get(0) : null;
        }


        public List<Position> findPosition( String pMMSI ) {
            
        Bson tKeyFilter= Filters.and( 
            Filters.eq("MMSI", pMMSI) );
    

        FindIterable<Document> tDocuments = this.mPositionCollection.find( tKeyFilter );
        if (tDocuments == null) {
           return null;
        }

        List<Position> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
           Document tDoc = tIter.next();
           Position tPosition = new Position();
           tPosition.decodeMongoDocument( tDoc );
           tResult.add( tPosition );
        }
        return tResult;
        }

        
            public List<Position> findPositionByMMSI( String pMMSI ) {
            List<Position> tResult = new ArrayList<>();
            
            Bson tKeyFilter= Filters.eq("MMSI", pMMSI); 

            FindIterable<Document> tDocuments  = this.mPositionCollection.find( tKeyFilter );
            MongoCursor<Document> tIter = tDocuments.iterator();
            while( tIter.hasNext()) {
            Document tDoc = tIter.next();
            Position tPosition = new Position();
            tPosition.decodeMongoDocument( tDoc );
            tResult.add(tPosition);
            }
            return tResult;
            }
        

            class DbKey {
                String      mKeyName;
                boolean     mUnique;

                DbKey( String pKeyName, boolean pUnique ) {
                    mKeyName = pKeyName;
                    mUnique = pUnique;
                }
            }

            }
        