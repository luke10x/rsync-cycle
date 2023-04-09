<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="mytags" prefix="my" %>

<t:genericpage>
    <jsp:attribute name="header">
      <jsp:include page="_header.jsp" />
    </jsp:attribute>
    <jsp:attribute name="footer">
      <jsp:include page="_footer.jsp" />
    </jsp:attribute>
  <jsp:body>
    <div class="container mx-auto my-8">
      <h1 class="text-2xl font-bold mb-4">Overview</h1>

      <c:if test="${not overview.active}">
        Overview is not active
      </c:if>
      <c:if test="${overview.active}">
        <div class="max-w-sm rounded overflow-hidden shadow-lg bg-white">
          <div class="w-full px-6">

            <c:set var="lightGray" value="#d1d5db" />
            <c:set var="darkGray"  value="#6b7280" /><!-- gray-500 -->
            <c:set var="lightBlue" value="#93c5fd" /><!-- blue-300 -->
            <c:set var="darkBlue"  value="#1d4ed8" /><!-- blue-800 -->

            <svg width="100%" height="100%" viewBox="0 0 42 42" class="donut">
              <circle class="donut-hole" cx="21" cy="21" r="15.91549430918954" fill="#fff"></circle>
              <circle class="donut-ring" cx="21" cy="21" r="15.91549430918954" fill="transparent"
                stroke="${lightGray}" stroke-width="3"></circle>

              <circle class="donut-segment" cx="21" cy="21" r="15.91549430918954" fill="transparent"
              stroke="${overview.getIdle() > 0 ? darkBlue : darkGray}" stroke-width="3"
                stroke-dasharray="${overview.getWork()} ${100 - overview.getWork()}" stroke-dashoffset="25"></circle>

              <circle class="donut-segment" cx="21" cy="21" r="15.91549430918954" fill="transparent" stroke="#93c5fd" stroke-width="3"
                stroke-dasharray="${overview.getIdle()} ${100 - overview.getIdle()}"
                stroke-dashoffset="${25 - overview.getWork()} "></circle>

              {* <rect x="0" y="0" width="42" height="42" stroke="#000" stroke-width="0" fill="transparent"/> *}
              <text x="21" y="21" text-anchor="middle" fill="${overview.getIdle() > 0 ? lightBlue : darkGray}" dominant-baseline="middle" font-size="3">
                <c:if test="${not overview.executing}">
                  IDLE
                </c:if>
                <c:if test="${overview.executing}">
                  IN ACTION
                </c:if>
              </text>
            </svg>
          </div>
          <div class="px-6 py-4">
            <div class="font-bold text-xl mb-2">Current Cycle</div>
            <p class="text-gray-700 text-base">
              This job is triggered at interval of:
            </p>
            <p class="text-gray-700 text-base">
              <my:hd duration="${overview.duration}" />
            </p>
            <div class="mt-4">
              <p class="text-gray-600 font-semibold">This provision
                <c:if test="${overview.executing}">
                  is running now
                </c:if>
                <c:if test="${not overview.executing}">
                  has been executed
                </c:if>
              </p>
              <p class="text-gray-700 ">
                <c:if test="${overview.executing}">
                  for <my:millis value="${overview.executionTime}" />
                </c:if>
                <c:if test="${not overview.executing}">
                  in <my:millis value="${overview.executionTime}" />
                </c:if>
              </p>
            </div>
            <div class="mt-4">
              <p class="text-gray-600 font-semibold">Next Scheduled Provision:</p>
              <p class="text-gray-700 ">${overview.getNextFireNiceDateTime()}</p>
              <p class="text-gray-700">
                (in <my:hd duration="${overview.remaining}" />)
              </p>
            </div>
          </div>
        </div>
      </c:if>

      <div class="py-4">
        <a href="/chores" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block">
          Continue shopping
        </a>
      </div>
    </div>
  </jsp:body>
</t:genericpage>
