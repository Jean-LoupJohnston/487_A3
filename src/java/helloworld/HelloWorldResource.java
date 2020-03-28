/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 * 
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package helloworld;



import com.google.gson.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import pkg487_a1_p1.*;
import thelibrarysystem.TheLibrarySystem;

/**
 *
 * @author Jean-Loup
 */
@Stateless
@Path("/library")
public class HelloWorldResource {

    
    TheLibrarySystem system; 

    public HelloWorldResource()throws SQLException, ClassNotFoundException, FileNotFoundException {
        this.system = TheLibrarySystem.getInstance();
    }

    @GET
    @Path("/listbooks")
    @Produces("text/plain")
    public String listBook() {
        try{
            String stringBooks = "";
            List<Book> books = system.showBooks();
            for(Book b: books)
            {
                stringBooks += b.toString()+"\n";
            }

            return stringBooks;
        }
        catch(Exception e)
        {
            return e.toString();
        }
        
    }
    
    @GET
    @Path("/listbooks/json")
    @Produces("application/json")
    public String listBookjson() {
        try{
            GsonBuilder builder = new GsonBuilder(); 
            builder.setPrettyPrinting(); 
            Gson gson = builder.create();
            List<Book> books = system.showBooks();
            String jsonInString = gson.toJson(books);

            return jsonInString;
        }
        catch(Exception e)
        {
            return e.toString();
        }
        
    }
    
     @GET
    @Path("/listbooks/xml")
    @Produces("application/xml")
    public String listBookXml() {
        try{
            List<Book> books = system.showBooks();
            Books bookList = new Books();
            bookList.setBooks(books);

            
            JAXBContext contextObj = JAXBContext.newInstance(Books.class);
            Marshaller marshallerObj = contextObj.createMarshaller();  
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            StringWriter sw = new StringWriter();
            marshallerObj.marshal(bookList,sw);

            return sw.toString();
        }
        catch(Exception e)
        {
            return e.toString();
        }
        
    }
    
   @GET
    @Path("/listbooks/html")
    @Produces("text/html")
    public String listBookhtml() {
        try{
            List<Book> books = system.showBooks();
            Books bookList = new Books();
            bookList.setBooks(books);

            
            JAXBContext contextObj = JAXBContext.newInstance(Books.class);
            Marshaller marshallerObj = contextObj.createMarshaller();  
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            StringWriter sw = new StringWriter();
            marshallerObj.marshal(bookList,sw);
            //transform to html with xslt file
            String xml = sw.toString();
            String html = "";
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Source xslDoc = new StreamSource("C:\\Users\\Jean-Loup\\Desktop\\487\\ServiceSystem\\a.xsl");
                Source xmlDoc = new StreamSource(new StringReader(xml));
                Transformer trasform = tFactory.newTransformer(xslDoc);
                StreamResult result = new StreamResult (new FileOutputStream("C:\\Users\\Jean-Loup\\Desktop\\487\\ServiceSystem\\output.txt"));
                trasform.transform(xmlDoc,result );
                Scanner scanner = new Scanner( new File("C:\\Users\\Jean-Loup\\Desktop\\487\\ServiceSystem\\output.txt") );
                String text = scanner.useDelimiter("\\A").next();
                scanner.close();
            return text;
        }
        catch(Exception e)
        {
            return e.toString();
        }
        
    }
    
    
    
    
    
    @GET
    @Path("/displaybook")
    @Produces("text/plain")
    public String displayBook(@QueryParam("id") int id) {
        
        if(system.getBookById(id)!= null)
        {
            return system.getBookById(id).toString();
        }
        return "Not found";
    }
    
     @GET
    @Path("/displaybook/json")
    @Produces("application/json")
    public String displayBookJson(@QueryParam("id") int id) {
        
        GsonBuilder builder = new GsonBuilder(); 
        builder.setPrettyPrinting(); 
        Gson gson = builder.create();
        if(system.getBookById(id)!= null)
        {
            
            String jsonInString = gson.toJson(system.getBookById(id));
            return jsonInString;
        }
        return gson.toJson("not found");
    }
    
    @GET
    @Path("/displaybook/xml")
    @Produces("application/xml")
    public String displayBookXml(@QueryParam("id") int id) {
        
        if(system.getBookById(id)!= null)
        {
            Book b = system.getBookById(id);
            try{
            JAXBContext contextObj = JAXBContext.newInstance(Book.class);
            Marshaller marshallerObj = contextObj.createMarshaller();  
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            StringWriter sw = new StringWriter();
            marshallerObj.marshal(b,sw);

            return sw.toString();
            }
            catch(Exception e)
            {
                return "error while searcng for book";
            }
        }
        return "Not found";
    }
    
    @POST
    @Path("/addbook")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String addbook(@FormParam("title") String t,@FormParam("description") String d,
                          @FormParam("isbn") String i,@FormParam("author") String a,
                          @FormParam("publisher") String p) {
        
        system.addBook(t, d, i, a, p);
        return "Book added";
    }
    
    @POST
    @Path("/addbook/json")
    @Produces("text/plain")
    @Consumes("application/json")
    public String addbookjson(String jsonBody) {
        Gson gson = new Gson();
        Book book = gson.fromJson(jsonBody, Book.class);
        system.addBook(book);
        return "book added";
    }
    
    @PUT
    @Path("/updatebook")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updatebook(@FormParam("id") int id,@FormParam("Attribute") int attr,
                          @FormParam("NewValue") String val) {
        
        system.updateBook(id, attr, val);
        return "Book updated";
    }
    
    @DELETE
    @Path("/deletebook/{id}")
    @Produces("text/plain")
    
    public String deletebook(@PathParam("id") int id) {
        
        return system.deleteBook(id);
        
    }

    
}
