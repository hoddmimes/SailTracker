
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
            public class User implements MessageInterface , MessageMongoInterface
            {
                public static String NAME = "User";

            
                private String mMongoId = null;
                    private String mMailAddr;
                    private String mMMSI;
                    private Boolean mIsBoatMMSI;
                    private String mPassword;
                    private String mSalt;
                    private String mLoginTime;
                    private String mLastLogin;
                    private Integer mLoginCounts;
                    private Boolean mCollecting;
                    private Integer mCollectFrequencyHH;
                    private Boolean mConfirmed;
                    private String mConfirmationId;
                    private Integer mShipId;
               public User()
               {
                
               }

               public User(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public String getMongoId() {
            return this.mMongoId;
            }

            public void setMongoId( String pMongoId ) {
            this.mMongoId = pMongoId;
            }
        
            public User setMailAddr( String pMailAddr ) {
            mMailAddr = pMailAddr;
            return this;
            }
            public Optional<String> getMailAddr() {
              return  Optional.ofNullable(mMailAddr);
            }
        
            public User setMMSI( String pMMSI ) {
            mMMSI = pMMSI;
            return this;
            }
            public Optional<String> getMMSI() {
              return  Optional.ofNullable(mMMSI);
            }
        
            public User setIsBoatMMSI( Boolean pIsBoatMMSI ) {
            mIsBoatMMSI = pIsBoatMMSI;
            return this;
            }
            public Optional<Boolean> getIsBoatMMSI() {
              return  Optional.ofNullable(mIsBoatMMSI);
            }
        
            public User setPassword( String pPassword ) {
            mPassword = pPassword;
            return this;
            }
            public Optional<String> getPassword() {
              return  Optional.ofNullable(mPassword);
            }
        
            public User setSalt( String pSalt ) {
            mSalt = pSalt;
            return this;
            }
            public Optional<String> getSalt() {
              return  Optional.ofNullable(mSalt);
            }
        
            public User setLoginTime( String pLoginTime ) {
            mLoginTime = pLoginTime;
            return this;
            }
            public Optional<String> getLoginTime() {
              return  Optional.ofNullable(mLoginTime);
            }
        
            public User setLastLogin( String pLastLogin ) {
            mLastLogin = pLastLogin;
            return this;
            }
            public Optional<String> getLastLogin() {
              return  Optional.ofNullable(mLastLogin);
            }
        
            public User setLoginCounts( Integer pLoginCounts ) {
            mLoginCounts = pLoginCounts;
            return this;
            }
            public Optional<Integer> getLoginCounts() {
              return  Optional.ofNullable(mLoginCounts);
            }
        
            public User setCollecting( Boolean pCollecting ) {
            mCollecting = pCollecting;
            return this;
            }
            public Optional<Boolean> getCollecting() {
              return  Optional.ofNullable(mCollecting);
            }
        
            public User setCollectFrequencyHH( Integer pCollectFrequencyHH ) {
            mCollectFrequencyHH = pCollectFrequencyHH;
            return this;
            }
            public Optional<Integer> getCollectFrequencyHH() {
              return  Optional.ofNullable(mCollectFrequencyHH);
            }
        
            public User setConfirmed( Boolean pConfirmed ) {
            mConfirmed = pConfirmed;
            return this;
            }
            public Optional<Boolean> getConfirmed() {
              return  Optional.ofNullable(mConfirmed);
            }
        
            public User setConfirmationId( String pConfirmationId ) {
            mConfirmationId = pConfirmationId;
            return this;
            }
            public Optional<String> getConfirmationId() {
              return  Optional.ofNullable(mConfirmationId);
            }
        
            public User setShipId( Integer pShipId ) {
            mShipId = pShipId;
            return this;
            }
            public Optional<Integer> getShipId() {
              return  Optional.ofNullable(mShipId);
            }
        

        public String getMessageName() {
        return "User";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = new JsonEncoder();
            pEncoder.add("User", tEncoder.toJson() );
            //Encode Attribute: mMailAddr Type: String List: false
            tEncoder.add( "mailAddr", mMailAddr );
        
            //Encode Attribute: mMMSI Type: String List: false
            tEncoder.add( "MMSI", mMMSI );
        
            //Encode Attribute: mIsBoatMMSI Type: boolean List: false
            tEncoder.add( "isBoatMMSI", mIsBoatMMSI );
        
            //Encode Attribute: mPassword Type: String List: false
            tEncoder.add( "password", mPassword );
        
            //Encode Attribute: mSalt Type: String List: false
            tEncoder.add( "salt", mSalt );
        
            //Encode Attribute: mLoginTime Type: String List: false
            tEncoder.add( "loginTime", mLoginTime );
        
            //Encode Attribute: mLastLogin Type: String List: false
            tEncoder.add( "lastLogin", mLastLogin );
        
            //Encode Attribute: mLoginCounts Type: int List: false
            tEncoder.add( "loginCounts", mLoginCounts );
        
            //Encode Attribute: mCollecting Type: boolean List: false
            tEncoder.add( "collecting", mCollecting );
        
            //Encode Attribute: mCollectFrequencyHH Type: int List: false
            tEncoder.add( "collectFrequencyHH", mCollectFrequencyHH );
        
            //Encode Attribute: mConfirmed Type: boolean List: false
            tEncoder.add( "confirmed", mConfirmed );
        
            //Encode Attribute: mConfirmationId Type: String List: false
            tEncoder.add( "confirmationId", mConfirmationId );
        
            //Encode Attribute: mShipId Type: int List: false
            tEncoder.add( "shipId", mShipId );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("User");
        
            //Decode Attribute: mMailAddr Type:String List: false
            mMailAddr = tDecoder.readString("mailAddr");
        
            //Decode Attribute: mMMSI Type:String List: false
            mMMSI = tDecoder.readString("MMSI");
        
            //Decode Attribute: mIsBoatMMSI Type:boolean List: false
            mIsBoatMMSI = tDecoder.readBoolean("isBoatMMSI");
        
            //Decode Attribute: mPassword Type:String List: false
            mPassword = tDecoder.readString("password");
        
            //Decode Attribute: mSalt Type:String List: false
            mSalt = tDecoder.readString("salt");
        
            //Decode Attribute: mLoginTime Type:String List: false
            mLoginTime = tDecoder.readString("loginTime");
        
            //Decode Attribute: mLastLogin Type:String List: false
            mLastLogin = tDecoder.readString("lastLogin");
        
            //Decode Attribute: mLoginCounts Type:int List: false
            mLoginCounts = tDecoder.readInteger("loginCounts");
        
            //Decode Attribute: mCollecting Type:boolean List: false
            mCollecting = tDecoder.readBoolean("collecting");
        
            //Decode Attribute: mCollectFrequencyHH Type:int List: false
            mCollectFrequencyHH = tDecoder.readInteger("collectFrequencyHH");
        
            //Decode Attribute: mConfirmed Type:boolean List: false
            mConfirmed = tDecoder.readBoolean("confirmed");
        
            //Decode Attribute: mConfirmationId Type:String List: false
            mConfirmationId = tDecoder.readString("confirmationId");
        
            //Decode Attribute: mShipId Type:int List: false
            mShipId = tDecoder.readInteger("shipId");
        

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
        
                pEncoder.add("mailAddr",  mMailAddr );
                pEncoder.add("MMSI",  mMMSI );
                pEncoder.add("isBoatMMSI",  mIsBoatMMSI );
                pEncoder.add("password",  mPassword );
                pEncoder.add("salt",  mSalt );
                pEncoder.add("loginTime",  mLoginTime );
                pEncoder.add("lastLogin",  mLastLogin );
                pEncoder.add("loginCounts",  mLoginCounts );
                pEncoder.add("collecting",  mCollecting );
                pEncoder.add("collectFrequencyHH",  mCollectFrequencyHH );
                pEncoder.add("confirmed",  mConfirmed );
                pEncoder.add("confirmationId",  mConfirmationId );
                pEncoder.add("shipId",  mShipId );
    }
    
        public void decodeMongoDocument( Document pDoc ) {

            Document tDoc = null;
            List<Document> tDocLst = null;
            MongoDecoder tDecoder = new MongoDecoder( pDoc );

            
            ObjectId _tId = pDoc.get("_id", ObjectId.class);
            this.mMongoId = _tId.toString();
            
           mMailAddr = tDecoder.readString("mailAddr");
        
           mMMSI = tDecoder.readString("MMSI");
        
           mIsBoatMMSI = tDecoder.readBoolean("isBoatMMSI");
        
           mPassword = tDecoder.readString("password");
        
           mSalt = tDecoder.readString("salt");
        
           mLoginTime = tDecoder.readString("loginTime");
        
           mLastLogin = tDecoder.readString("lastLogin");
        
           mLoginCounts = tDecoder.readInteger("loginCounts");
        
           mCollecting = tDecoder.readBoolean("collecting");
        
           mCollectFrequencyHH = tDecoder.readInteger("collectFrequencyHH");
        
           mConfirmed = tDecoder.readBoolean("confirmed");
        
           mConfirmationId = tDecoder.readString("confirmationId");
        
           mShipId = tDecoder.readInteger("shipId");
        
        } // End decodeMongoDocument
    

        public static  Builder getUserBuilder() {
            return new User.Builder();
        }


        public static class  Builder {
          private User mInstance;

          private Builder () {
            mInstance = new User();
          }

        
                        public Builder setMailAddr( String pValue ) {
                        mInstance.setMailAddr( pValue );
                        return this;
                    }
                
                        public Builder setMMSI( String pValue ) {
                        mInstance.setMMSI( pValue );
                        return this;
                    }
                
                        public Builder setIsBoatMMSI( Boolean pValue ) {
                        mInstance.setIsBoatMMSI( pValue );
                        return this;
                    }
                
                        public Builder setPassword( String pValue ) {
                        mInstance.setPassword( pValue );
                        return this;
                    }
                
                        public Builder setSalt( String pValue ) {
                        mInstance.setSalt( pValue );
                        return this;
                    }
                
                        public Builder setLoginTime( String pValue ) {
                        mInstance.setLoginTime( pValue );
                        return this;
                    }
                
                        public Builder setLastLogin( String pValue ) {
                        mInstance.setLastLogin( pValue );
                        return this;
                    }
                
                        public Builder setLoginCounts( Integer pValue ) {
                        mInstance.setLoginCounts( pValue );
                        return this;
                    }
                
                        public Builder setCollecting( Boolean pValue ) {
                        mInstance.setCollecting( pValue );
                        return this;
                    }
                
                        public Builder setCollectFrequencyHH( Integer pValue ) {
                        mInstance.setCollectFrequencyHH( pValue );
                        return this;
                    }
                
                        public Builder setConfirmed( Boolean pValue ) {
                        mInstance.setConfirmed( pValue );
                        return this;
                    }
                
                        public Builder setConfirmationId( String pValue ) {
                        mInstance.setConfirmationId( pValue );
                        return this;
                    }
                
                        public Builder setShipId( Integer pValue ) {
                        mInstance.setShipId( pValue );
                        return this;
                    }
                

        public User build() {
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

    