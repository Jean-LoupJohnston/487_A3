<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <body>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th>ID</th>
        <th>Title</th>
		<th>Description</th>
		<th>ISBN</th>
		<th>Author</th>
		<th>Publisher</th>
      </tr>
      <xsl:for-each select="books/book">
        <tr>
          <td><xsl:value-of select="id"/></td>
          <td><xsl:value-of select="title"/></td>
		  <td><xsl:value-of select="description"/></td>
		  <td><xsl:value-of select="isbn"/></td>
		  <td><xsl:value-of select="author"/></td>
		  <td><xsl:value-of select="publisher"/></td>
        </tr>
      </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>