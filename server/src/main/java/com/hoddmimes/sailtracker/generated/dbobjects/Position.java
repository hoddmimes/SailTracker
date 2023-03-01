
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
package com.hoddmimes.sailtracker.generated.dbobjects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.io.IOException;




    import org.bson.BsonType;
    import org.bson.Document;
    import org.bson.conversions.Bson;
    import com.mongodb.BasicDBObject;
    import org.bson.types.ObjectId;
    import com.hoddmimes.jsontransform.MessageMongoInterface;
    import com.hoddmimes.jsontransform.MongoDecoder;
    import com.hoddmimes.jsontransform.MongoEncoder;


import com.hoddmimes.jsontransform.MessageInterface;
import com.hoddmimes.jsontransform.JsonDecoder;
import com.hoddmimes.jsontransform.JsonEncoder;
import com.hoddmimes.jsontransform.ListFactory;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



            

            @SuppressWarnings({"WeakerAccess","unused","unchecked"})
            public class Position implements MessageInterface , MessageMongoInterface
            {
                public static String NAME = "Position";

            
                private String mMongoId = null;
                    private String mTime;
                    private String mReportTime;
                    private String mSource;
                    private String mMMSI;
                    private Double mLat;
                    private Double mLong;
               public Position()
               {
                
               }

               public Position(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public String getMongoId() {
            return this.mMongoId;
            }

            public void setMongoId( String pMongoId ) {
            this.mMongoId = pMongoId;
            }
        
            public Position setTime( String pTime ) {
            mTime = pTime;
            return this;
            }
            public Optional<String> getTime() {
              return  Optional.ofNullable(mTime);
            }
        
            public Position setReportTime( String pReportTime ) {
            mReportTime = pReportTime;
            return this;
            }
            public Optional<String> getReportTime() {
              return  Optional.ofNullable(mReportTime);
            }
        
            public Position setSource( String pSource ) {
            mSource = pSource;
            return this;
            }
            public Optional<String> getSource() {
              return  Optional.ofNullable(mSource);
            }
        
            public Position setMMSI( String pMMSI ) {
            mMMSI = pMMSI;
            return this;
            }
            public Optional<String> getMMSI() {
              return  Optional.ofNullable(mMMSI);
            }
        
            public Position setLat( Double pLat ) {
            mLat = pLat;
            return this;
            }
            public Optional<Double> getLat() {
              return  Optional.ofNullable(mLat);
            }
        
            public Position setLong( Double pLong ) {
            mLong = pLong;
            return this;
            }
            public Optional<Double> getLong() {
              return  Optional.ofNullable(mLong);
            }
        

        public String getMessageName() {
        return "Position";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = new JsonEncoder();
            pEncoder.add("Position", tEncoder.toJson() );
            //Encode Attribute: mTime Type: String List: false
            tEncoder.add( "time", mTime );
        
            //Encode Attribute: mReportTime Type: String List: false
            tEncoder.add( "reportTime", mReportTime );
        
            //Encode Attribute: mSource Type: String List: false
            tEncoder.add( "source", mSource );
        
            //Encode Attribute: mMMSI Type: String List: false
            tEncoder.add( "MMSI", mMMSI );
        
            //Encode Attribute: mLat Type: double List: false
            tEncoder.add( "lat", mLat );
        
            //Encode Attribute: mLong Type: double List: false
            tEncoder.add( "long", mLong );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("Position");
        
            //Decode Attribute: mTime Type:String List: false
            mTime = tDecoder.readString("time");
        
            //Decode Attribute: mReportTime Type:String List: false
            mReportTime = tDecoder.readString("reportTime");
        
            //Decode Attribute: mSource Type:String List: false
            mSource = tDecoder.readString("source");
        
            //Decode Attribute: mMMSI Type:String List: false
            mMMSI = tDecoder.readString("MMSI");
        
            //Decode Attribute: mLat Type:double List: false
            mLat = tDecoder.readDouble("lat");
        
            //Decode Attribute: mLong Type:double List: false
            mLong = tDecoder.readDouble("long");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    
        public Document getMongoDocument() {
            MongoEncoder tEncoder = new MongoEncoder();
            
            mongoEncode( tEncoder );
            return tEncoder.getDoc();
        }

     protected void mongoEncode(  MongoEncoder pEncoder ) {
        
                pEncoder.add("time",  mTime );
                pEncoder.add("reportTime",  mReportTime );
                pEncoder.add("source",  mSource );
                pEncoder.add("MMSI",  mMMSI );
                pEncoder.add("lat",  mLat );
                pEncoder.add("long",  mLong );
    }
    
        public void decodeMongoDocument( Document pDoc ) {

            Document tDoc = null;
            List<Document> tDocLst = null;
            MongoDecoder tDecoder = new MongoDecoder( pDoc );

            
            ObjectId _tId = pDoc.get("_id", ObjectId.class);
            this.mMongoId = _tId.toString();
            
           mTime = tDecoder.readString("time");
        
           mReportTime = tDecoder.readString("reportTime");
        
           mSource = tDecoder.readString("source");
        
           mMMSI = tDecoder.readString("MMSI");
        
           mLat = tDecoder.readDouble("lat");
        
           mLong = tDecoder.readDouble("long");
        
        } // End decodeMongoDocument
    

        public static  Builder getPositionBuilder() {
            return new Position.Builder();
        }


        public static class  Builder {
          private Position mInstance;

          private Builder () {
            mInstance = new Position();
          }

        
                        public Builder setTime( String pValue ) {
                        mInstance.setTime( pValue );
                        return this;
                    }
                
                        public Builder setReportTime( String pValue ) {
                        mInstance.setReportTime( pValue );
                        return this;
                    }
                
                        public Builder setSource( String pValue ) {
                        mInstance.setSource( pValue );
                        return this;
                    }
                
                        public Builder setMMSI( String pValue ) {
                        mInstance.setMMSI( pValue );
                        return this;
                    }
                
                        public Builder setLat( Double pValue ) {
                        mInstance.setLat( pValue );
                        return this;
                    }
                
                        public Builder setLong( Double pValue ) {
                        mInstance.setLong( pValue );
                        return this;
                    }
                

        public Position build() {
            return mInstance;
        }

        }
    
            }
            
        /**
            Possible native attributes
            o "boolean" mapped to JSON "Boolean"
            o "byte" mapped to JSON "Integer"
            o "char" mapped to JSON "Integer"
            o "short" mapped to JSON "Integer"
            o "int" mapped to JSON "Integer"
            o "long" mapped to JSON "Integer"
            o "double" mapped to JSON "Numeric"
            o "String" mapped to JSON "String"
            o "byte[]" mapped to JSON "String" (Base64 string)


            An attribute could also be an "constant" i.e. having the property "constantGroup", should then refer to an defined /Constang/Group
             conastants are mapped to JSON strings,


            If the type is not any of the types below it will be refer to an other structure / object

        **/

    