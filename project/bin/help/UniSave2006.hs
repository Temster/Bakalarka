<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE helpset
  PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN"
         "http://java.sun.com/products/javahelp/helpset_2_0.dtd">
<helpset version="2.0">
   <!-- title --> 
   <title>Nápověda programu UniSave 2006</title>
			
   <!-- maps --> 
   <maps>
     <homeID>index</homeID>
     <mapref location="UniSave2006.jhm" />
   </maps>
	
   <!-- views --> 
   <view xml:lang="cs" mergetype="javax.help.UniteAppendMerge">
      <name>TOC</name>
      <label>Obsah</label>
      <type>javax.help.TOCView</type>
      <data>UniSave2006HelpTOC.xml</data>
   </view>
	
   <view xml:lang="cs" mergetype="javax.help.SortMerge">
      <name>Index</name>
      <label>Index</label>
      <type>javax.help.IndexView</type>
      <data>UniSave2006HelpIndex.xml</data>
   </view>
	
   <view xml:lang="cs">
      <name>Search</name>
      <label>Hledat</label>
      <type>javax.help.SearchView</type>
         <data engine="com.sun.java.help.search.DefaultSearchEngine">
         JavaHelpSearch
         </data>
   </view>

   <!-- A glossary navigator 
   <view  mergetype="javax.help.SortMerge">
      <name>glossary</name>
      <label>Glossary</label>
      <type>javax.help.GlossaryView</type>
      <data>glossary.xml</data>
   </view-->

   <!-- A favorites navigator -->
   <view xml:lang="cs">
      <name>favorites</name>
      <label>Oblíbené</label>
      <type>javax.help.FavoritesView</type>
   </view>

   <!-- subhelpsets > 
   <subhelpset location="file:/c:/Foobar/HelpSet2.hs" /-->

   <!-- presentation windows -->

   <!-- This window is the default one for the helpset. 
     *  Its title bar says "Project X Help". It
     *  is a tri-paned window because displayviews, not
     *  defined, defaults to true and because a toolbar is defined.
     *  The toolbar has a back arrow, a forward arrow, and
     *  a home button that has a user-defined image.
   -->
   <presentation default="true" xml:lang="cs">
       <name>main window</name>
       <size width="800" height="600" /> 
       <location x="200" y="100" />
       <title>UniSave 2006 - Nápověda</title>
       <image>UniSaveIcon</image>
       <toolbar>
           <helpaction>javax.help.BackAction</helpaction>
           <helpaction>javax.help.ForwardAction</helpaction>
           <helpaction image="homeicon">javax.help.HomeAction</helpaction>
           <helpaction>javax.help.SeparatorAction</helpaction>
           <helpaction>javax.help.PrintAction</helpaction>
           <helpaction>javax.help.PrintSetupAction</helpaction>
       </toolbar>
   </presentation>

   <!-- This window is simpler than the main window. 
     *  It's intended to be used a secondary window.
     *  It has no navigation pane or toolbar.
   -->
   <presentation displayviews="false">
       <name>secondary window</name>
       <size width="200" height="200" /> 
       <location x="200" y="200" />
   </presentation>
 

   <!-- implementation section -->
   <impl>
      <helpsetregistry helpbrokerclass="javax.help.DefaultHelpBroker" />
      <viewerregistry viewertype="text/html" 
         viewerclass="com.sun.java.help.impl.CustomKit" />
      <viewerregistry viewertype="text/xml" viewerclass="com.sun.java.help.impl.CustomXMLKit" />
   </impl>
</helpset>
