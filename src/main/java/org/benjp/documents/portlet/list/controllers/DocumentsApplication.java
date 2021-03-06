package org.benjp.documents.portlet.list.controllers;

import juzu.*;
import juzu.bridge.portlet.JuzuPortlet;
import juzu.plugin.ajax.Ajax;
import juzu.request.RequestContext;
import juzu.template.Template;

import org.apache.commons.fileupload.FileItem;
import org.benjp.documents.portlet.list.bean.File;
import org.benjp.documents.portlet.list.bean.Folder;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.logging.Logger;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
@SessionScoped
public class DocumentsApplication
{

  //Logger log = Logger.getLogger("DocumentsApplication");
  private static final Log log = ExoLogger.getLogger(DocumentsApplication.class.getName());

  @Inject
  Provider<PortletPreferences> providerPreferences;

  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  @Inject
  @Path("edit.gtmpl")
  Template editTemplate;

  @Inject
  @Path("properties.gtmpl")
  Template propertiesTemplate;

  @Inject
  DocumentsData documentsData;

  @Inject java.util.ResourceBundle bundle;


  @View
  public Response.Content index(RequestContext renderContext) throws IOException
  {
	  
	this.log.info("start the portlet here.");
    PortletPreferences portletPreferences = providerPreferences.get();
    String refresh = portletPreferences.getValue("refresh", "10");
    if ("".equals(refresh)) refresh="10";

    PortletMode portletMode = renderContext.getProperty(JuzuPortlet.PORTLET_MODE);
    log.debug("Portlet mode: {}", portletMode);
    if (portletMode.equals(PortletMode.VIEW))
    {
      String space = documentsData.getSpaceName();
      String context;
      if (space!=null)
      {
        context = "Community";
      }
      else
      {
        context = "Personal";
        space = "";
      }

      return indexTemplate.with().set("filter", DocumentsData.TYPE_DOCUMENT)
              .set("context", context).set("space", space)
              .set("refresh", refresh)
              .ok();
    }
    else {
      log.debug("initialize documents Date");
      documentsData.initNodetypes();
      return editTemplate.with().set("refresh", refresh).ok();

    }

  }

  @Action
  public void save(String refresh)
  {
    try {
      PortletPreferences portletPreferences = providerPreferences.get();
      portletPreferences.setValue("refresh", refresh);
      portletPreferences.store();
    } catch (ReadOnlyException e) {
      e.printStackTrace();
    } catch (ValidatorException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Resource
  @Ajax
  public Response.Content getFiles(String filter)
  {
    log.info("getFiles::" + filter);
    try
    {
      Folder folder = documentsData.getNodes(filter);
      return Response.ok(folder.filesToJSON()).withMimeType("text/event-stream; charset=UTF-8").withHeader("Cache-Control", "no-cache");

    }
    catch (Exception e)
    {
      log.error(filter, e);
      return Response.notFound("error");
    }
  }

  @Resource
  @Ajax
  public Response.Content checkTimestamp(String filter, String timestamp)
  {
//    log.info("checkTimestamp::"+filter+"::"+timestamp);
//    sleep(2);
    try
    {


      Long timestampNew = documentsData.getTimestamp(filter);

      if (!(""+timestampNew).equals(timestamp))
      {
        Folder folder = documentsData.getNodes(filter);
        return Response.ok(folder.filesToJSON()).withMimeType("text/event-stream; charset=UTF-8").withHeader("Cache-Control", "no-cache");
      }
      else
      {
        return Response.ok("{\"timestamp\": \""+timestamp+"\", \"hasData\": false}").withMimeType("text/event-stream; charset=UTF-8").withHeader("Cache-Control", "no-cache");
      }

    }
    catch (Exception e)
    {
      return Response.notFound("error");
    }
  }

  @Resource
  @Ajax
  public Response.Content upload(String appContext, String appSpace, String appFilter, String dataUuid, FileItem pic, RequestContext resourceContext) {

    boolean isPrivateContext = "Personal".equals(appContext);
    String name = (isPrivateContext)?resourceContext.getSecurityContext().getRemoteUser():appSpace;

    if (pic != null)
    {
      try{
    	  if (dataUuid!=null)
          {
            documentsData.storeFile(appFilter, pic, name, isPrivateContext, dataUuid);
            return Response.ok("<div style='background-color:#ffa; padding:20px'>File has been uploaded successfully!</div>")
                    .withMimeType("text/html; charset=UTF-8").withHeader("Cache-Control", "no-cache");
          }
          else
          {
            documentsData.storeFile(appFilter, pic, name, isPrivateContext);
            return Response.ok("{\"status\":\"File has been uploaded successfully!\"}")
                    .withMimeType("application/json; charset=UTF-8").withHeader("Cache-Control", "no-cache");
          }
      }catch(Exception e){
    	  String errorMsg = "file uploading error - " + e.getMessage();
    	  return Response.notFound("{\"error\":\"" + errorMsg + "\"}");
      }
    }

    return Response.notFound("{\"error\":\"file not found\"}");
  }

  @Resource
  @Ajax
  public Response.Content getProperties(String uuid, String path)
  {
    File file;
    if (uuid!=null && !"".equals(uuid))
      file = documentsData.getNode(uuid);
    else
      file = documentsData.getNode(path);

    Response.Content content = propertiesTemplate.with().set("file", file).ok();
    return content;
  }

  @Resource
  @Ajax
  public void restore(String uuid, String name)
  {
    documentsData.restoreVersion(uuid, name);
    propertiesTemplate.with().set("file", documentsData.getNode(uuid)).ok();
  }

  @Resource
  @Ajax
  public Response.Content deleteFile(String uuid, String path)
  {
    try
    {
      if (uuid!=null && !"".equals(uuid))
        documentsData.deleteFile(uuid);
      else
        documentsData.deleteFile(path);
    }
    catch (Exception e)
    {
      return Response.notFound(getMessage("benjp.documents.message.error.delete"));
    }
    return Response.ok(getMessage("benjp.documents.message.success.delete"));
  }

  @Resource
  @Ajax
  public Response.Content renameFile(String uuid, String name, String path)
  {
    try
    {
      if (uuid!=null && !"".equals(uuid))
        documentsData.renameFile(uuid, name);
      else
        documentsData.renameFile(path, name);

    }
    catch (IllegalArgumentException e)
    {
      return Response.notFound(e.getMessage());
    }
    catch (Exception e)
    {
      return Response.notFound(getMessage("benjp.documents.message.error.rename"));
    }
    return Response.ok(getMessage("benjp.documents.message.success.rename"));
  }

  @Resource
  @Ajax
  public Response.Content newFolder(String documentFilter, String name)
  {
    if (!documentsData.createNodeIfNotExist(documentFilter, name))
    {
      return Response.notFound(getMessage("benjp.documents.message.error.folder"));
    }
    return Response.ok(getMessage("benjp.documents.message.success.folder"));
  }

  @Resource
  @Ajax
  public Response.Content editTags(String uuid, String tags)
  {
    try
    {
      documentsData.editTags(uuid, tags);
    }
    catch (IllegalArgumentException e)
    {
      return Response.notFound(e.getMessage());
    }
    catch (Exception e)
    {
      return Response.notFound(getMessage("benjp.documents.message.error.tags"));
    }
    return Response.ok(getMessage("benjp.documents.message.success.tags"));
  }


  private String getMessage(String key) {
    try {
      return bundle.getString(key);
    } catch (MissingResourceException mre) {
      log.info("no resource for key: "+key);
    }
    return "";
  }

}
