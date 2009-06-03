<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<style type="text/css">
<!--

#blue-box {
	font-family: Arial, Helvetica, sans-serif;
	display: block;
	height: 341px;
	width: 931px;
	background-image: url(<c:url value="/images/foop/blue-box.png"/>);
}

#blue-box table {
	margin-left: 50px;
}

#blue-box a {
	color: white;
	font-size: 36px;
	text-decoration: none;
	display: block;
}

#blue-box a:hover {
	text-decoration: underline;
}

#blue-box a img {
	border: none;
}

#blue-box td {
	vertical-align: top;
	padding-top: 13px;
}

#blue-box span {
	color: #aec7f2;
	font-size: 0.8em;
	display: block;
	width: 300px;
}

col.icon {
	width: 75px;
}

col.text {
	width: 350px;
}
-->
</style>

		<div id="blue-box">
            <table>
                <tbody>
                    <col class="icon">
                                        <col class="text">
                                        <col class="icon">
                                        <col class="text">
                    <tr>
                        <td>
                            <a href="/web/guest/catalog/all"><img src="<c:url value="/images/foop/folder.png"/>" alt="" /></a>
                        </td>
                        <td>
                            <a href="/web/guest/catalog/all">Browse</a>
                            <span>Browse through caBIG data sets, tools, people, institutions, communities, and more.</span>
                        </td>
                        <td>
                            <a href="/web/guest/catalog/contribute"><img src="<c:url value="/images/foop/pencil.png"/>" alt="" /></a>
                        </td>
                        <td>
                            <a href="/web/guest/catalog/contribute">Contribute</a>
                            <span>Share your knowledge, data, and tools with the community.</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a href="/web/guest/tools/search"><img src="<c:url value="/images/foop/magnifying-glass.png"/>" alt="" /></a>
                        </td>
                        <td>
                            <a href="/web/guest/tools/search">Search</a>
                            <span>Search through all caBIG data sources.</span>
                        </td>
                        <td>
                            <a href="/web/guest/communities/collaborate"><img src="<c:url value="/images/foop/people.png"/>" alt="" /></a>
                        </td>
                        <td>
                            <a href="/web/guest/communities/collaborate">Collaborate</a>
                            <span>Organize communities around your interests.</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a href="/web/guest/catalog/tools"><img src="<c:url value="/images/foop/wrench.png"/>" alt="" /></a>
                        </td>
                        <td>
                            <a href="/web/guest/catalog/tools">Interact</a>
                            <span>Work with caBIG tools and execute scientific workflows.</span>
                        </td>
						<td>
                            <a href="/web/guest/about-cagrid-portal"><img src="<c:url value="/images/foop/question-mark.png"/>" alt="" /></a>
                        </td>
                        <td>
                            <a href="/web/guest/about-cagrid-portal">About caGRID Portal</a>
                            <span>Learn more about what you can do here.</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>