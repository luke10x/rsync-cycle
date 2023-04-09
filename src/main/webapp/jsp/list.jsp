<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<t:genericpage>
  <jsp:attribute name="header">
    <jsp:include page="_header.jsp" />
  </jsp:attribute>
  <jsp:attribute name="footer">
    <jsp:include page="_footer.jsp" />
  </jsp:attribute>
  <jsp:body>
    <div class="container mx-auto my-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-4">List of Chores (${chores.size()})</h1>
      <table class="table-auto w-full">
        <thead>
          <tr>
            <th class="px-4 py-2">ID</th>
            <th class="px-4 py-2">Title</th>
            <th class="px-4 py-2">Type</th>
            <th class="px-4 py-2">Interval</th>
            <th class="px-4 py-2">Status</th>
            <th class="px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          <%-- loop through the list of chores and display them in a table --%>
          <c:forEach items="${chores}" var="chore">
            <tr>
              <td class="border px-4 py-2">${chore.id}</td>
              <td class="border px-4 py-2">${chore.title}</td>
              <td class="border px-4 py-2">${chore.type}</td>
              <td class="border px-4 py-2">${chore.interval}</td>
              <td class="border px-4 py-2 ${chore.active ? 'text-green-500' : 'text-gray-400'}">
                ${chore.active ? 'STARTED' : 'PAUSED'}
              </td>
              <td class="border px-4 py-2">
                <a href="/overview/${chore.id}" class="text-blue-500 hover:text-blue-800 mr-2">Overview</a>
                <a href="/chores/${chore.id}/edit" class="text-blue-500 hover:text-blue-800 mr-2">Edit</a>
                <form method="POST" action="${pageContext.request.contextPath}/chores/${chore.id}/start" class="inline mr-2">
                  <input
                    ${chore.active ? 'disabled' : ''}
                    class="bg-transparent	cursor-pointer disabled:opacity-50 text-blue-500 hover:text-blue-800"
                    type="submit"
                    value="Start"
                   />
                </form>
                <form method="POST" action="${pageContext.request.contextPath}/chores/${chore.id}/pause" class="inline mr-2">
                  <input
                    ${chore.active ? '' : 'disabled'}
                    class="bg-transparent	cursor-pointer disabled:opacity-50 text-blue-500 hover:text-blue-800"
                    type="submit"
                    value="Pause"
                  />
                </form>
                <form method="POST" action="/chores/${chore.id}/delete"
                    class="inline text-pink-500 hover:text-pink-800 mr-2">
                  <input
                    class="bg-transparent	cursor-pointer"
                    type="submit"
                    value="Delete"
                   />
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <div class="mt-4">

       <a href="/chores/init"
        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block">
          Create New Chore
       </a>
      </div>
    </div>
  </jsp:body>
</t:genericpage>
