
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
package com.hoddmimes.te.messages.generated;

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
        
            import com.hoddmimes.te.messages.*;

            @SuppressWarnings({"WeakerAccess","unused","unchecked"})
            public class StatusMessage implements MessageInterface 
            {
                public static String NAME = "StatusMessage";

            
                    private Boolean mIsOk;
                    private String mRef;
                    private String mStatusMessage;
                    private String mExceptionMessage;
               public StatusMessage()
               {
                
               }

               public StatusMessage(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public StatusMessage setIsOk( Boolean pIsOk ) {
            mIsOk = pIsOk;
            return this;
            }
            public Optional<Boolean> getIsOk() {
              return  Optional.ofNullable(mIsOk);
            }
        
            public StatusMessage setRef( String pRef ) {
            mRef = pRef;
            return this;
            }
            public Optional<String> getRef() {
              return  Optional.ofNullable(mRef);
            }
        
            public StatusMessage setStatusMessage( String pStatusMessage ) {
            mStatusMessage = pStatusMessage;
            return this;
            }
            public Optional<String> getStatusMessage() {
              return  Optional.ofNullable(mStatusMessage);
            }
        
            public StatusMessage setExceptionMessage( String pExceptionMessage ) {
            mExceptionMessage = pExceptionMessage;
            return this;
            }
            public Optional<String> getExceptionMessage() {
              return  Optional.ofNullable(mExceptionMessage);
            }
        

        public String getMessageName() {
        return "StatusMessage";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = new JsonEncoder();
            pEncoder.add("StatusMessage", tEncoder.toJson() );
            //Encode Attribute: mIsOk Type: boolean List: false
            tEncoder.add( "isOk", mIsOk );
        
            //Encode Attribute: mRef Type: String List: false
            tEncoder.add( "ref", mRef );
        
            //Encode Attribute: mStatusMessage Type: String List: false
            tEncoder.add( "statusMessage", mStatusMessage );
        
            //Encode Attribute: mExceptionMessage Type: String List: false
            tEncoder.add( "exceptionMessage", mExceptionMessage );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("StatusMessage");
        
            //Decode Attribute: mIsOk Type:boolean List: false
            mIsOk = tDecoder.readBoolean("isOk");
        
            //Decode Attribute: mRef Type:String List: false
            mRef = tDecoder.readString("ref");
        
            //Decode Attribute: mStatusMessage Type:String List: false
            mStatusMessage = tDecoder.readString("statusMessage");
        
            //Decode Attribute: mExceptionMessage Type:String List: false
            mExceptionMessage = tDecoder.readString("exceptionMessage");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getStatusMessageBuilder() {
            return new StatusMessage.Builder();
        }


        public static class  Builder {
          private StatusMessage mInstance;

          private Builder () {
            mInstance = new StatusMessage();
          }

        
                        public Builder setIsOk( Boolean pValue ) {
                        mInstance.setIsOk( pValue );
                        return this;
                    }
                
                        public Builder setRef( String pValue ) {
                        mInstance.setRef( pValue );
                        return this;
                    }
                
                        public Builder setStatusMessage( String pValue ) {
                        mInstance.setStatusMessage( pValue );
                        return this;
                    }
                
                        public Builder setExceptionMessage( String pValue ) {
                        mInstance.setExceptionMessage( pValue );
                        return this;
                    }
                

        public StatusMessage build() {
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

    