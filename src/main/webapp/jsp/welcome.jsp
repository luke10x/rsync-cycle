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
  		<h1 class="text-3xl font-bold text-gray-900 mb-4">Welcome to RSync Cycle</h1>
  		<p class="text-gray-700 mb-8">
  		Welcome to Rsync-Cycle - your ultimate solution for automating Rsync-based data synchronization!
  		Rsync-Cycle is a powerful tool that allows you to schedule and run Rsync-based provisions at configured intervals,
  		making it easy to keep your data in sync across multiple devices and locations.


  		This app helps you manage your file synchronization tasks using Rsync command. With Chores Manager, you can easily schedule your Rsync tasks, monitor their status, and view the activity logs.
  		</p>


      <div class="flex flex-col md:flex-row items-center justify-center">
        <!-- Manage Chores Card -->
        <div class="bg-white rounded-lg shadow-lg p-6 mb-4 md:mb-0 md:mr-4 flex-grow md:flex-grow-0">
          <h2 class="text-xl font-bold mb-4">Create and Manage Chores</h2>
          <p class="mb-4">The main feature of this app is the ability to create and manage chores. With chores, you can specify the source and destination directories, the Rsync options, and the schedule for the transfer. Once a chore is created, it can be edited or deleted as needed.</p>
  		  	<a href="/chores" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block">Manage Chores</a>
        </div>

        <!-- Activity Log Card -->
        <div class="bg-white rounded-lg shadow-lg p-6 mb-4 md:mb-0 md:mx-2 flex-grow md:flex-grow-0">
          <h2 class="text-xl font-bold mb-4">Monitor Activity Log</h2>
          <p class="mb-4">Another feature of this app is the ability to monitor the activity log. The activity log keeps track of all the transfers made by the chores. This allows you to monitor the status of each transfer and troubleshoot any issues that may arise.</p>
     			<a href="/activity-log" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block">Activity Log</a>
        </div>

        <!-- H2 Console Card -->
        <div class="bg-white rounded-lg shadow-lg p-6 flex-grow md:flex-grow-0">
          <h2 class="text-xl font-bold mb-4">Access H2 Console</h2>
          <p class="mb-4">For advanced users, this app provides access to the H2 Console. The H2 Console is a web-based database management tool that allows you to manage the app's database. With the H2 Console, you can view and edit the app's data, and perform advanced queries.</p>
  	  		<a target="_blank" href="/h2-console" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block">H2 Console</a>
        </div>
      </div>

  		<p class="text-gray-700 mt-8 mb-8">If you encounter any issues or need help with Chores Manager,
  		please contact our support team.</p>

  	</div>
  </jsp:body>
</t:genericpage>
