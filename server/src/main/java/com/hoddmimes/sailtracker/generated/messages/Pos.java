
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
            public class Pos implements MessageInterface 
            {
                public static String NAME = "Pos";

            
                    private String mTime;
                    private Double mLat;
                    private Double mLong;
                    private String mId;
               public Pos()
               {
                
               }

               public Pos(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public Pos setTime( String pTime ) {
            mTime = pTime;
            return this;
            }
            public Optional<String> getTime() {
              return  Optional.ofNullable(mTime);
            }
        
            public Pos setLat( Double pLat ) {
            mLat = pLat;
            return this;
            }
            public Optional<Double> getLat() {
              return  Optional.ofNullable(mLat);
            }
        
            public Pos setLong( Double pLong ) {
            mLong = pLong;
            return this;
            }
            public Optional<Double> getLong() {
              return  Optional.ofNullable(mLong);
            }
        
            public Pos setId( String pId ) {
            mId = pId;
            return this;
            }
            public Optional<String> getId() {
              return  Optional.ofNullable(mId);
            }
        

        public String getMessageName() {
        return "Pos";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = pEncoder;
            //Encode Attribute: mTime Type: String List: false
            tEncoder.add( "time", mTime );
        
            //Encode Attribute: mLat Type: double List: false
            tEncoder.add( "lat", mLat );
        
            //Encode Attribute: mLong Type: double List: false
            tEncoder.add( "long", mLong );
        
            //Encode Attribute: mId Type: String List: false
            tEncoder.add( "id", mId );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder;
        
            //Decode Attribute: mTime Type:String List: false
            mTime = tDecoder.readString("time");
        
            //Decode Attribute: mLat Type:double List: false
            mLat = tDecoder.readDouble("lat");
        
            //Decode Attribute: mLong Type:double List: false
            mLong = tDecoder.readDouble("long");
        
            //Decode Attribute: mId Type:String List: false
            mId = tDecoder.readString("id");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    
            public Pos( Position pPosition ) {
               this.mTime = pPosition.getTime().get();
               this.mLat = pPosition.getLat().get();
               this.mLong = pPosition.getLong().get();
               this.mId = pPosition.getMongoId();
            }
        

        public static  Builder getPosBuilder() {
            return new Pos.Builder();
        }


        public static class  Builder {
          private Pos mInstance;

          private Builder () {
            mInstance = new Pos();
          }

        
                        public Builder setTime( String pValue ) {
                        mInstance.setTime( pValue );
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
                
                        public Builder setId( String pValue ) {
                        mInstance.setId( pValue );
                        return this;
                    }
                

        public Pos build() {
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

    