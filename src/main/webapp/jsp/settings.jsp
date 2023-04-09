<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:genericpage>
  <jsp:attribute name="header">
    <jsp:include page="_header.jsp" />
  </jsp:attribute>
  <jsp:attribute name="footer">
    <jsp:include page="_footer.jsp" />
  </jsp:attribute>
  <jsp:body>

  	<div class="container mx-auto px-4 py-8">
  		<h1 class="text-3xl font-bold text-gray-900 mb-4">Settings</h1>


<div class="flex flex-col md:flex-row flex items-stretch ">
  <!-- Card 1: Import -->
  <div class="bg-white rounded-lg shadow-lg p-6 mb-4 md:mb-0 md:mr-4 flex-grow md:flex-grow-0 flex flex-col justify-between items-end"">
    <div>
      <h2 class="text-xl font-bold mb-4">Import Chores</h2>
      <p class="mb-4">Import your existing list of chores from a YAML file. This feature allows you to easily get started with the app if you already have an existing list of chores that you'd like to manage.</p>
    </div>
    <form method="post" enctype="multipart/form-data" action="/settings/import" class="flex items-end">
      <input type="file" name="file" id="file" class="border rounded-lg py-1 px-3 mr-2 flex-grow">
      <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block whitespace-nowrap">Import Chores</button>
    </form>
  </div>

  <!-- Card 2: Export  -->
  <div class="bg-white rounded-lg shadow-lg p-6 mb-4 md:mb-0 flex-grow md:flex-grow-0 flex flex-col justify-between items-end">
    <div>
      <h2 class="text-xl font-bold mb-4 flex-grow">Export Chores</h2>
      <p class="mb-4">Export your current list of chores as a YAML file. This feature allows you to easily share your list of chores with others or back up your list of chores.</p>
    </div>
    <a href="/settings/export"
    class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block flex-grow-0 whitespace-nowrap">Export Chores</a>
  </div>
</div>



  	</div>
  </jsp:body>
</t:genericpage>
