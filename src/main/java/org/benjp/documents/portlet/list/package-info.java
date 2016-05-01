@Application(defaultController = org.benjp.documents.portlet.list.controllers.DocumentsApplication.class)
@Portlet
@Bindings(
        {
                @Binding(value = org.exoplatform.services.jcr.RepositoryService.class),
                @Binding(value = org.exoplatform.services.jcr.ext.app.SessionProviderService.class),
                @Binding(value = org.exoplatform.services.cms.folksonomy.NewFolksonomyService.class),
                @Binding(value = org.exoplatform.services.cms.link.LinkManager.class),
                @Binding(value = org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator.class)
        }

)

@Less(@Stylesheet("documents.less"))
@Assets("*")

@WebJars(@WebJar("jquery"))

@Scripts({
	            @Script(id = "jquery", value = "jquery/1.8.3/jquery.js"),
                @Script(value = "js/jquery-juzu-utils-0.1.0.js", depends = "jquery", id="juzuutil"),
                @Script(value = "js/jquery.filedrop.js", depends = "jquery", id = "filedrop"),
                @Script(value = "js/mustache.js", id="mustache"),
                @Script(value = "js/taffy-min.js", id="taffy"),
                @Script(value = "js/main.js", depends = {"jquery", "juzuutil", "filedrop", "mustache", "taffy"}, location=AssetLocation.APPLICATION)
})
    
    @Stylesheets ({
                @Stylesheet(value = "/org/benjp/documents/portlet/list/assets/documents.css", location = AssetLocation.APPLICATION),
                @Stylesheet(value = "css/bootstrap.min.css", location = AssetLocation.SERVER, id="bootstrap")
               // @Stylesheet(value = "/org/benjp/documents/portlet/list/assets/documents.css", location = AssetLocation.APPLICATION)
               // bootstrap.min.css
        })


package org.benjp.documents.portlet.list;

import juzu.Application;
import juzu.asset.AssetLocation;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.asset.Scripts;
import juzu.plugin.asset.Stylesheet;
import juzu.plugin.asset.Stylesheets;
import juzu.plugin.binding.Binding;
import juzu.plugin.binding.Bindings;
import juzu.plugin.less4j.Less;
import juzu.plugin.portlet.Portlet;
import juzu.plugin.webjars.WebJar;
import juzu.plugin.webjars.WebJars;




import org.benjp.provider.GateInMetaProvider;

