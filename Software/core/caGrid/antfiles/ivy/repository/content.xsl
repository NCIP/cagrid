<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!--
  BSD License for IvyRep
Copyright (c) 2005, JAYASOFT
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, 
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, 
      this list of conditions and the following disclaimer in the documentation 
      and/or other materials provided with the distribution.
    * Neither the name of JAYASOFT nor the names of its contributors 
      may be used to endorse or promote products derived from this software 
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
  -->
  <xsl:template match="/ivy-rep">
    <xsl:variable name="organisations" select="/ivy-rep/organisation"/>
    <xsl:variable name="modules" select="/ivy-rep/organisation/module"/>
    <xsl:variable name="revisions" select="/ivy-rep/organisation/module/revision"/>

    <html>
      <head>
        <title>Ivy Repository Content</title>
        <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
        <meta http-equiv="content-language" content="en"/>
        <meta name="robots" content="index,follow"/>
        <link rel="stylesheet" type="text/css" href="content.css"/>
      </head>
      <body>
        <div id="logo">
          <a href="http://ivy.jayasoft.org/">
            <img src="http://ivy.jayasoft.org/logo.png"/>
          </a>
          <br/>
          <a id="rep" href="http://ivyrep.jayasoft.org/">Ivy Repository</a>
        </div>
        <h1>Ivy Repository Content</h1>
        <div id="stats">
          <xsl:value-of select="count($organisations)"/> organisations,<br/>
          <xsl:value-of select="count($modules)"/> modules,<br/>
          <xsl:value-of select="count($revisions)"/> revisions<br/>
        </div>
        <table>
          <xsl:for-each select="$organisations">
            <xsl:variable name="org" select="@name"/>
            <tr class="org">
              <td colspan="2">
                <h2>
                  <xsl:element name="a">
                    <xsl:attribute name="href">
                      <xsl:value-of select="$org"/>
                    </xsl:attribute>
                    <xsl:value-of select="$org"/>
                  </xsl:element>
                </h2>
              </td>
            </tr>
            <xsl:for-each select="module">
              <xsl:variable name="mod" select="@name"/>
              <tr>
                <td class="mod">
                  <h3>
                    <xsl:element name="a">
                      <xsl:attribute name="href"><xsl:value-of select="$org"/>/<xsl:value-of
                          select="$mod"/></xsl:attribute>
                      <xsl:value-of select="$mod"/>
                    </xsl:element>
                  </h3>
                </td>
                <td class="rev">
                  <xsl:for-each select="revision">
                    <div>
                      <xsl:element name="a">
                        <xsl:attribute name="href"><xsl:value-of select="$org"/>/<xsl:value-of
                            select="$mod"/>/ivy-<xsl:value-of select="@name"/>.xml</xsl:attribute>
                        <xsl:value-of select="@name"/>
                      </xsl:element>
                    </div>
                  </xsl:for-each>
                </td>
              </tr>
            </xsl:for-each>
          </xsl:for-each>
        </table>
        <div id="copyright"> file generated by <a href="http://ivyrep.jayasoft.org/">ivyrep content
            generator</a> by <a href="http://www.jayasoft.org/">jayasoft</a><br/>
        </div>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
