
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

import com.hoddmimes.jsontransform.*;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NameNotFoundException;

	
import com.hoddmimes.sailtracker.generated.messages.*;
	

@SuppressWarnings({"WeakerAccess","unused","unchecked"})
public class MessageFactory implements MessageFactoryInterface
{
	public static Pattern JSON_MESSAGE_NAME_PATTERN = Pattern.compile("^\\s*\\{\\s*\"(\\w*)\"\\s*:\\s*\\{");


	public String getJsonMessageId( String pJString ) throws NameNotFoundException
	{
		Matcher tMatcher = JSON_MESSAGE_NAME_PATTERN.matcher(pJString);
		if (tMatcher.find()) {
		  return tMatcher.group(1);
		}
		throw new NameNotFoundException("Failed to extract message id from JSON message");
	}

	@Override
	public MessageInterface getMessageInstance(String pJsonMessageString) {
		String tMessageId = null;

		try { tMessageId = getJsonMessageId( pJsonMessageString ); }
		catch( NameNotFoundException e ) { return null; }
	
		switch( tMessageId ) 
		{

            case "LogonRqst":
            {
            	LogonRqst tMessage = new LogonRqst();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "Response":
            {
            	Response tMessage = new Response();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "RegistrationRqst":
            {
            	RegistrationRqst tMessage = new RegistrationRqst();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "MailConfirmation":
            {
            	MailConfirmation tMessage = new MailConfirmation();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "UpdatePasswordRqst":
            {
            	UpdatePasswordRqst tMessage = new UpdatePasswordRqst();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "PositionRsp":
            {
            	PositionRsp tMessage = new PositionRsp();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "SettingsRsp":
            {
            	SettingsRsp tMessage = new SettingsRsp();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "UpdateSettingsRqst":
            {
            	UpdateSettingsRqst tMessage = new UpdateSettingsRqst();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "AddPositionRqst":
            {
            	AddPositionRqst tMessage = new AddPositionRqst();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "SummaryRsp":
            {
            	SummaryRsp tMessage = new SummaryRsp();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "DeleteSelectedRqst":
            {
            	DeleteSelectedRqst tMessage = new DeleteSelectedRqst();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "PositionsForMapRqst":
            {
            	PositionsForMapRqst tMessage = new PositionsForMapRqst();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "PositionsForMapRsp":
            {
            	PositionsForMapRsp tMessage = new PositionsForMapRsp();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            case "RecoverPasswordRqst":
            {
            	RecoverPasswordRqst tMessage = new RecoverPasswordRqst();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            default:
              return null;
		}	
	}
}

