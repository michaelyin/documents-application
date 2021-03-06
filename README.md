# migrate juzu from 0.6 to 1.1.0 for eXo platform 4.3.0
https://community.exoplatform.com/portal/g/:spaces:juzu/juzu/wiki/Migration_Guide

# Documents Application

The goal of this app is to provide a simple Document management app inside eXo Platform 3.5.

So, basically, my requirements are simple :  
- Allow to Drag n Drop multiple files directly from your Desktop to the browser
- Simple rename, Delete
- View properties like the size of each document
- Preview with thumbnail generation
- Link to download each file using WebDAV
- Public link to share the file to others
- Allow to tag files
- View by Documents, Images or Tags
- Upload new Version
- View Versions and Restore a previous Version
- Automatic Private User context or Space context in the Intranet.

This app was developped with eXo Platform 3.5 for the portal and documents stuff, Juzu to write the app and Bootstrap for the design.

## need add adn namespace
In Administration -> Content -> Content Management i go to Repository -> Namespaces, 
and Register New Namespace with Namespace Prefix : adn & URL : http://www.exoplatform.com/jcr/adn/1.0

## eXo Powa Inside !

If you want to develop on top of eXo Platform, you can find a lot of resources to help you here :
http://community.exoplatform.com/portal/classic/documentation-public

The same with Juzu : http://juzuweb.org

Just to say it, Juzu is fun to develop with and eXo Platform is full of features to achieve my goal.

## Some Screenshots

### Drag n Drop : 
![alt text](https://raw.github.com/benjp/documents/master/screenshots/02-dragndrop.png "Drag n Drop")

### Properties : 
![alt text](https://raw.github.com/benjp/documents/master/screenshots/03-properties.png "View Properties")

### Preview : 
![alt text](https://raw.github.com/benjp/documents/master/screenshots/04-preview.png "Preview")

### Public Sharing Link : 
![alt text](https://raw.github.com/benjp/documents/master/screenshots/05-sharing.png "Public Sharing")

### Tags : 
![alt text](https://raw.github.com/benjp/documents/master/screenshots/06-tags.png "Tags")

### View by Tags : 
![alt text](https://raw.github.com/benjp/documents/master/screenshots/07-filter-tags.png "View by Tags")

### View and Restore Versions : 
![alt text](https://raw.github.com/benjp/documents/master/screenshots/08-versions.png "View and Restore Versions")

### Private and Space contexts : 
![alt text](https://raw.github.com/benjp/documents/master/screenshots/09-space.png "Space Context")

### Upload New Version : 
![alt text](https://raw.github.com/benjp/documents/master/screenshots/10-upload.png "Upload New Version")


## Complete Demo from how to install it to how to use it :

<p><a href="http://vimeo.com/50831296" target="_vimeo"><img src="https://raw.github.com/benjp/documents/master/screenshots/01-general.png"></img></a></p><p>from <a href="http://vimeo.com/user1241097" target="_vimeo">Benjamin Paillereau</a> on <a href="http://vimeo.com" target="_vimeo">Vimeo</a>.</p>

### exoplatform blog :
<a href="https://www.exoplatform.com/blog/2013/02/06/developing-a-simple-document-management-exo-add-on"> blog </a>

<a href="https://www.exoplatform.com/blog/2013/01/15/exo-add-ons-creating-photo-galleries"> photo galleries blog </a>

