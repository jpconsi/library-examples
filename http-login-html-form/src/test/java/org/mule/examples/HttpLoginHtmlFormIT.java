/**
 * MuleSoft Examples
 * Copyright 2014 MuleSoft, Inc.
 *
 * This product includes software developed at
 * MuleSoft, Inc. (http://www.mulesoft.com/).
 */

package org.mule.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class HttpLoginHtmlFormIT extends FunctionalTestCase
{

    private static final String REPLY = "User mule has been logged successfully!";
	private static String HTML;

    @Override
    protected String getConfigResources()
    {
        return "http-login-html-form.xml";
    }

    @BeforeClass
    public static void init(){
    	try {
			HTML = IOUtils.toString(new FileInputStream("./src/main/resources/login/index.html"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }       
    
    @Test
	public void testLogin() throws Exception {    
    	MuleClient client = new MuleClient(muleContext);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("http.method", "POST");
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", "mule");
        map.put("password", "mule");
        MuleMessage result = client.send("http://localhost:8081/login", map, props);
        assertTrue(result.getPayloadAsString().contains(REPLY));
            	       
	}
    
    @Test
	public void testLoginPage() throws Exception {        	
    	MuleClient client = new MuleClient(muleContext);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("http.method", "GET");
        MuleMessage result = client.send("http://localhost:8081/login", "", props);
        assertEquals(result.getPayloadAsString(), HTML);        
	}
    
    @Test
	public void testRequester() throws Exception {        	
    	MuleClient client = new MuleClient(muleContext);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("http.method", "GET");
        MuleMessage result = client.send("http://localhost:8081/requesterLogin", "", props);
        assertTrue(result.getPayloadAsString().contains(REPLY));        
	}    
}

