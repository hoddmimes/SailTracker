
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
package com.hoddmimes.sailtracker.generated.messages;

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





import com.hoddmimes.jsontransform.MessageInterface;
import com.hoddmimes.jsontransform.JsonDecoder;
import com.hoddmimes.jsontransform.JsonEncoder;
import com.hoddmimes.jsontransform.ListFactory;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



            
        // Add XML defined imports
        
            import com.hoddmimes.sailtracker.generated.dbobjects.Position;

            @SuppressWarnings({"WeakerAccess","unused","unchecked"})
            public class SummaryRsp implements MessageInterface 
            {
                public static String NAME = "SummaryRsp";

            
                    private Integer mTotalUsers;
                    private Integer mTotalPositions;
                    private String mTotalFirstEver;
                    private String mMMSI;
                    private String mMmsiLastLogin;
                    private Integer mMmsiPositions;
                    private Pos mMmsiFirstPos;
                    private Pos mMmsiLastPos;
               public SummaryRsp()
               {
                
               }

               public SummaryRsp(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public SummaryRsp setTotalUsers( Integer pTotalUsers ) {
            mTotalUsers = pTotalUsers;
            return this;
            }
            public Optional<Integer> getTotalUsers() {
              return  Optional.ofNullable(mTotalUsers);
            }
        
            public SummaryRsp setTotalPositions( Integer pTotalPositions ) {
            mTotalPositions = pTotalPositions;
            return this;
            }
            public Optional<Integer> getTotalPositions() {
              return  Optional.ofNullable(mTotalPositions);
            }
        
            public SummaryRsp setTotalFirstEver( String pTotalFirstEver ) {
            mTotalFirstEver = pTotalFirstEver;
            return this;
            }
            public Optional<String> getTotalFirstEver() {
              return  Optional.ofNullable(mTotalFirstEver);
            }
        
            public SummaryRsp setMMSI( String pMMSI ) {
            mMMSI = pMMSI;
            return this;
            }
            public Optional<String> getMMSI() {
              return  Optional.ofNullable(mMMSI);
            }
        
            public SummaryRsp setMmsiLastLogin( String pMmsiLastLogin ) {
            mMmsiLastLogin = pMmsiLastLogin;
            return this;
            }
            public Optional<String> getMmsiLastLogin() {
              return  Optional.ofNullable(mMmsiLastLogin);
            }
        
            public SummaryRsp setMmsiPositions( Integer pMmsiPositions ) {
            mMmsiPositions = pMmsiPositions;
            return this;
            }
            public Optional<Integer> getMmsiPositions() {
              return  Optional.ofNullable(mMmsiPositions);
            }
        

            public Optional<Pos> getMmsiFirstPos() {
              return  Optional.ofNullable(mMmsiFirstPos);
            }

            public SummaryRsp setMmsiFirstPos(Pos pMmsiFirstPos) {
            mMmsiFirstPos = pMmsiFirstPos;
            return this;
            }

        

            public Optional<Pos> getMmsiLastPos() {
              return  Optional.ofNullable(mMmsiLastPos);
            }

            public SummaryRsp setMmsiLastPos(Pos pMmsiLastPos) {
            mMmsiLastPos = pMmsiLastPos;
            return this;
            }

        

        public String getMessageName() {
        return "SummaryRsp";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = new JsonEncoder();
            pEncoder.add("SummaryRsp", tEncoder.toJson() );
            //Encode Attribute: mTotalUsers Type: int List: false
            tEncoder.add( "totalUsers", mTotalUsers );
        
            //Encode Attribute: mTotalPositions Type: int List: false
            tEncoder.add( "totalPositions", mTotalPositions );
        
            //Encode Attribute: mTotalFirstEver Type: String List: false
            tEncoder.add( "totalFirstEver", mTotalFirstEver );
        
            //Encode Attribute: mMMSI Type: String List: false
            tEncoder.add( "MMSI", mMMSI );
        
            //Encode Attribute: mMmsiLastLogin Type: String List: false
            tEncoder.add( "mmsiLastLogin", mMmsiLastLogin );
        
            //Encode Attribute: mMmsiPositions Type: int List: false
            tEncoder.add( "mmsiPositions", mMmsiPositions );
        
            //Encode Attribute: mMmsiFirstPos Type: Pos List: false
            tEncoder.add( "mmsiFirstPos", mMmsiFirstPos );
        
            //Encode Attribute: mMmsiLastPos Type: Pos List: false
            tEncoder.add( "mmsiLastPos", mMmsiLastPos );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("SummaryRsp");
        
            //Decode Attribute: mTotalUsers Type:int List: false
            mTotalUsers = tDecoder.readInteger("totalUsers");
        
            //Decode Attribute: mTotalPositions Type:int List: false
            mTotalPositions = tDecoder.readInteger("totalPositions");
        
            //Decode Attribute: mTotalFirstEver Type:String List: false
            mTotalFirstEver = tDecoder.readString("totalFirstEver");
        
            //Decode Attribute: mMMSI Type:String List: false
            mMMSI = tDecoder.readString("MMSI");
        
            //Decode Attribute: mMmsiLastLogin Type:String List: false
            mMmsiLastLogin = tDecoder.readString("mmsiLastLogin");
        
            //Decode Attribute: mMmsiPositions Type:int List: false
            mMmsiPositions = tDecoder.readInteger("mmsiPositions");
        
            //Decode Attribute: mMmsiFirstPos Type:Pos List: false
            mMmsiFirstPos = (Pos) tDecoder.readMessage( "mmsiFirstPos", Pos.class );
        
            //Decode Attribute: mMmsiLastPos Type:Pos List: false
            mMmsiLastPos = (Pos) tDecoder.readMessage( "mmsiLastPos", Pos.class );
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getSummaryRspBuilder() {
            return new SummaryRsp.Builder();
        }


        public static class  Builder {
          private SummaryRsp mInstance;

          private Builder () {
            mInstance = new SummaryRsp();
          }

        
                        public Builder setTotalUsers( Integer pValue ) {
                        mInstance.setTotalUsers( pValue );
                        return this;
                    }
                
                        public Builder setTotalPositions( Integer pValue ) {
                        mInstance.setTotalPositions( pValue );
                        return this;
                    }
                
                        public Builder setTotalFirstEver( String pValue ) {
                        mInstance.setTotalFirstEver( pValue );
                        return this;
                    }
                
                        public Builder setMMSI( String pValue ) {
                        mInstance.setMMSI( pValue );
                        return this;
                    }
                
                        public Builder setMmsiLastLogin( String pValue ) {
                        mInstance.setMmsiLastLogin( pValue );
                        return this;
                    }
                
                        public Builder setMmsiPositions( Integer pValue ) {
                        mInstance.setMmsiPositions( pValue );
                        return this;
                    }
                
                    public Builder setMmsiFirstPos( Pos pValue )  {
                        mInstance.setMmsiFirstPos( pValue );
                        return this;
                    }
                
                    public Builder setMmsiLastPos( Pos pValue )  {
                        mInstance.setMmsiLastPos( pValue );
                        return this;
                    }
                

        public SummaryRsp build() {
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

    