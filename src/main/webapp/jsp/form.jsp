<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="dev.luke10x.rsynccycle.management.ChoreType" %>

<t:genericpage>
    <jsp:attribute name="header">
      <jsp:include page="_header.jsp" />
    </jsp:attribute>
    <jsp:attribute name="footer">
      <jsp:include page="_footer.jsp" />
    </jsp:attribute>
  <jsp:body>
    <div class="container mx-auto my-8">
      <h1 class="text-2xl font-bold mb-4">
        <c:if test="${not empty chore.id}">
          Chore #${chore.id}
        </c:if>
        <c:if test="${empty chore.id}">
          New Chore
        </c:if>
      </h1>
      <c:if test="${hasErrors}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 my-3 rounded relative" role="alert">
          <span class="block sm:inline">There were errors in the form. Please correct them and try again.</span>
        </div>
      </c:if>

      <form:form
        action="${empty chore.id ? '/chores/new' : '/chores/'.concat(chore.id)}"
        method="POST"
        modelAttribute="chore"
      >

        <form:input path="id" type="hidden" />
        <form:input path="type" type="hidden" />
        <form:input path="active" type="hidden" />
        <form:label path="type" class="block text-gray-700 font-bold mb-2">This chore is ${chore.type}</form:label>

        <div class="mb-4">
          <form:label path="title" class="block text-gray-700 font-bold mb-2">Title:</form:label>
          <form:input path="title" type="text" class="w-full border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
          <form:errors path="title" cssClass="text-red-500"/>
        </div>

        <div class="mb-4">
          <form:label path="interval" class="block text-gray-700 font-bold mb-2">Plan frequency (in seconds):</form:label>
          <form:input path="interval" type="number" class="w-full border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
          <form:errors path="interval" cssClass="text-red-500"/>
        </div>

        <c:set var="typesThatNeedSsh"
          value="${{ChoreType.REMOTE_SSH, ChoreType.REMOTE_SSH_CONTAINER}}" />
        <c:if test="${typesThatNeedSsh.contains(chore.type)}">
          <div class="mb-4">
            <form:label path="user" class="block text-gray-700 font-bold mb-2">SSH User</form:label>
            <form:input path="user" type="text" class="w-full border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
            <form:errors path="user" cssClass="text-red-500"/>
          </div>

          <div class="mb-4">
            <form:label path="host" class="block text-gray-700 font-bold mb-2">SSH Host</form:label>
            <form:input path="host" type="text" class="w-full border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
            <form:errors path="host" cssClass="text-red-500"/>
          </div>

          <div class="mb-4">
            <form:label path="port" class="block text-gray-700 font-bold mb-2">SSH Port</form:label>
            <form:input path="port" type="number" class="w-full border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
            <form:errors path="port" cssClass="text-red-500"/>
          </div>

          <div class="mb-4">
            <form:label path="publicKey" class="block text-gray-700 font-bold mb-2">SSH Public Key</form:label>
            <form:textarea path="publicKey" rows="5" class="w-full border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"></form:textarea>
            <form:errors path="publicKey" cssClass="text-red-500"/>
          </div>
        </c:if>

        <c:set var="typesThatNeedContainer"
          value="${{ChoreType.LOCAL_CONTAINER, ChoreType.REMOTE_CONTAINER, ChoreType.REMOTE_SSH_CONTAINER}}" />
        <c:if test="${typesThatNeedContainer.contains(chore.type)}">
          <div class="mb-4">
            <form:label path="container" class="block text-gray-700 font-bold mb-2">Container name</form:label>
            <form:input path="container" type="text" class="w-full border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
            <form:errors path="container" cssClass="text-red-500"/>
          </div>
        </c:if>

        <div class="mb-4">
          <form:label path="source" class="block text-gray-700 font-bold mb-2">Source Path</form:label>
          <form:input path="source" type="text" class="w-full border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
          <form:errors path="source" cssClass="text-red-500"/>
        </div>

        <div class="mb-4">
          <form:label path="destination" class="block text-gray-700 font-bold mb-2">Destination Path</form:label>
          <form:input path="destination" type="text" class="w-full border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
          <form:errors path="destination" cssClass="text-red-500"/>
        </div>

        <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          Save Chore
        </button>
        <a href="/chores" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block">
          Cancel
        </a>
      </form:form>
    </div>
  </jsp:body>
</t:genericpage>
