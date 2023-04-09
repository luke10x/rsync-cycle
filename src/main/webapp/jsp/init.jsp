<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="header">
      <jsp:include page="_header.jsp" />
    </jsp:attribute>
    <jsp:attribute name="footer">
      <jsp:include page="_footer.jsp" />
    </jsp:attribute>
  <jsp:body>
    <div class="container mx-auto my-8">
      <h1 class="text-2xl font-bold mb-4">Select source type</h1>
      <form:form action="/chores/new" method="GET" modelAttribute="chore">
        <div class="my-4 flex gap-3">
            <form:radiobutton path="type" id="local" value="LOCAL" checked="true" />
            <form:label path="type" for="local">
              <span class="font-bold">Local</span>
              <p class="text-gray-600">Select this option if the source path is on the local machine.</p>
            </form:label>
        </div>
        <div class="my-4 flex gap-3">
            <form:radiobutton path="type" id="remote-ssh" value="REMOTE_SSH" />
            <form:label path="type" for="remote-ssh">
              <span class="font-bold">Remote using SSH</span>
              <p class="text-gray-600">
                Select this option if the source path is on a remote machine that can be accessed using SSH.</p>
            </form:label>
        </div>
        <div class="my-4 flex gap-3">
            <form:radiobutton path="type" id="local-container" value="LOCAL_CONTAINER" />
            <form:label path="type" for="local-container">
              <span class="font-bold">Local Container</span>
              <p class="text-gray-600">Select this option if the source path is on a container
              running on the local machine.</p>
            </form:label>
        </div>
        <div class="my-4 flex gap-3">
            <form:radiobutton path="type" id="remote-container" value="REMOTE_CONTAINER" />
            <form:label path="type" for="remote-container">
              <span class="font-bold">Remote Container using Docker API</span>
              <p class="text-gray-600">
                Select this option if the source path is on a container running on a remote machine
                that can be accessed using the Docker API.</p>
            </form:label>
        </div>
        <div class="my-4 flex gap-3">
            <form:radiobutton path="type" id="remote-ssh-container" name="type" value="REMOTE_SSH_CONTAINER" />
            <form:label path="type" for="remote-ssh-container">
              <span class="font-bold">Remote Container using SSH</span>
              <p class="text-gray-600">Select this option if the source path is on a container
                running on a remote machine that can be accessed using SSH.</p>
            </form:label>
        </div>

        <button type="submit"
          class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
        >
          Create Chore
        </button>
        <a href="/chores" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block">
          Cancel
        </a>
      </form:form>
    </div>
  </jsp:body>
</t:genericpage>
