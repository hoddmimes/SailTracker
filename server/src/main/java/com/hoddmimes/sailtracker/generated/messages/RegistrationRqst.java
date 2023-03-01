
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
            public class RegistrationRqst implements MessageInterface 
            {
                public static String NAME = "RegistrationRqst";

            
                    private String mMailAddress;
                    private String mPassword;
                    private String mMMSI;
               public RegistrationRqst()
               {
                
               }

               public RegistrationRqst(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public RegistrationRqst setMailAddress( String pMailAddress ) {
            mMailAddress = pMailAddress;
            return this;
            }
            public Optional<String> getMailAddress() {
              return  Optional.ofNullable(mMailAddress);
            }
        
            public RegistrationRqst setPassword( String pPassword ) {
            mPassword = pPassword;
            return this;
            }
            public Optional<String> getPassword() {
              return  Optional.ofNullable(mPassword);
            }
        
            public RegistrationRqst setMMSI( String pMMSI ) {
            mMMSI = pMMSI;
            return this;
            }
            public Optional<String> getMMSI() {
              return  Optional.ofNullable(mMMSI);
            }
        

        public String getMessageName() {
        return "RegistrationRqst";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = new JsonEncoder();
            pEncoder.add("RegistrationRqst", tEncoder.toJson() );
            //Encode Attribute: mMailAddress Type: String List: false
            tEncoder.add( "mailAddress", mMailAddress );
        
            //Encode Attribute: mPassword Type: String List: false
            tEncoder.add( "password", mPassword );
        
            //Encode Attribute: mMMSI Type: String List: false
            tEncoder.add( "MMSI", mMMSI );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("RegistrationRqst");
        
            //Decode Attribute: mMailAddress Type:String List: false
            mMailAddress = tDecoder.readString("mailAddress");
        
            //Decode Attribute: mPassword Type:String List: false
            mPassword = tDecoder.readString("password");
        
            //Decode Attribute: mMMSI Type:String List: false
            mMMSI = tDecoder.readString("MMSI");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getRegistrationRqstBuilder() {
            return new RegistrationRqst.Builder();
        }


        public static class  Builder {
          private RegistrationRqst mInstance;

          private Builder () {
            mInstance = new RegistrationRqst();
          }

        
                        public Builder setMailAddress( String pValue ) {
                        mInstance.setMailAddress( pValue );
                        return this;
                    }
                
                        public Builder setPassword( String pValue ) {
                        mInstance.setPassword( pValue );
                        return this;
                    }
                
                        public Builder setMMSI( String pValue ) {
                        mInstance.setMMSI( pValue );
                        return this;
                    }
                

        public RegistrationRqst build() {
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

    