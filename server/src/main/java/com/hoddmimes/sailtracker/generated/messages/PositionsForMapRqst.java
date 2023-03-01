
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
            public class PositionsForMapRqst implements MessageInterface 
            {
                public static String NAME = "PositionsForMapRqst";

            
                    private String mPositionId;
                    private String mMmsiId;
               public PositionsForMapRqst()
               {
                
               }

               public PositionsForMapRqst(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public PositionsForMapRqst setPositionId( String pPositionId ) {
            mPositionId = pPositionId;
            return this;
            }
            public Optional<String> getPositionId() {
              return  Optional.ofNullable(mPositionId);
            }
        
            public PositionsForMapRqst setMmsiId( String pMmsiId ) {
            mMmsiId = pMmsiId;
            return this;
            }
            public Optional<String> getMmsiId() {
              return  Optional.ofNullable(mMmsiId);
            }
        

        public String getMessageName() {
        return "PositionsForMapRqst";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = new JsonEncoder();
            pEncoder.add("PositionsForMapRqst", tEncoder.toJson() );
            //Encode Attribute: mPositionId Type: String List: false
            tEncoder.add( "positionId", mPositionId );
        
            //Encode Attribute: mMmsiId Type: String List: false
            tEncoder.add( "mmsiId", mMmsiId );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("PositionsForMapRqst");
        
            //Decode Attribute: mPositionId Type:String List: false
            mPositionId = tDecoder.readString("positionId");
        
            //Decode Attribute: mMmsiId Type:String List: false
            mMmsiId = tDecoder.readString("mmsiId");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getPositionsForMapRqstBuilder() {
            return new PositionsForMapRqst.Builder();
        }


        public static class  Builder {
          private PositionsForMapRqst mInstance;

          private Builder () {
            mInstance = new PositionsForMapRqst();
          }

        
                        public Builder setPositionId( String pValue ) {
                        mInstance.setPositionId( pValue );
                        return this;
                    }
                
                        public Builder setMmsiId( String pValue ) {
                        mInstance.setMmsiId( pValue );
                        return this;
                    }
                

        public PositionsForMapRqst build() {
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

    