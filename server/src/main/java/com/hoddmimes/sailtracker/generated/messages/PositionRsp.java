
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
            public class PositionRsp implements MessageInterface 
            {
                public static String NAME = "PositionRsp";

            
                    private List<Pos> mPositions;
               public PositionRsp()
               {
                
               }

               public PositionRsp(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public PositionRsp setPositions( List<Pos> pPositions ) {
              if (pPositions == null) {
                mPositions = null;
                return this;
              }


            if ( mPositions == null)
            mPositions = ListFactory.getList("array");


            mPositions .addAll( pPositions );
            return this;
            }


            public PositionRsp addPositions( List<Pos> pPositions ) {

            if ( mPositions == null)
            mPositions = ListFactory.getList("array");

            mPositions .addAll( pPositions );
            return this;
            }

            public PositionRsp addPositions( Pos pPositions ) {

            if ( pPositions == null) {
            return this; // Not supporting null in vectors, well design issue
            }

            if ( mPositions == null) {
            mPositions = ListFactory.getList("array");
            }

            mPositions.add( pPositions );
            return this;
            }


            public Optional<List<Pos>> getPositions() {

            if (mPositions == null) {
                return  Optional.ofNullable(null);
            }

             //List<Pos> tList = ListFactory.getList("array");
             //tList.addAll( mPositions );
             // return  Optional.ofNullable(tList);
             return Optional.ofNullable(mPositions);
            }

        

        public String getMessageName() {
        return "PositionRsp";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = new JsonEncoder();
            pEncoder.add("PositionRsp", tEncoder.toJson() );
            //Encode Attribute: mPositions Type: Pos List: true
            tEncoder.addMessageArray("positions", mPositions );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("PositionRsp");
        
            //Decode Attribute: mPositions Type:Pos List: true
            mPositions = (List<Pos>) tDecoder.readMessageArray( "positions", "array", Pos.class );
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getPositionRspBuilder() {
            return new PositionRsp.Builder();
        }


        public static class  Builder {
          private PositionRsp mInstance;

          private Builder () {
            mInstance = new PositionRsp();
          }

        
                    public Builder setPositions( List<Pos> pValue )  {
                        mInstance.setPositions( pValue );
                        return this;
                    }
                

        public PositionRsp build() {
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

    