<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="UTF-8">
      <title>RSync Cycle</title>
      <link rel="icon" type="image/svg+xml" href="/favicon.svg"></link>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="https://unpkg.com/tailwindcss@2.2.19/dist/tailwind.min.css">
      <script src="https://cdn.tailwindcss.com"></script>
      <script>
        tailwind.config = {
          theme: {
            extend: {
              extend: {
                opacity: ['disabled'],
              }
            }
          }
        }
      </script>
  </head>
  <body class="flex flex-col min-h-screen">
    <div id="pageheader">
      <jsp:invoke fragment="header"/>
    </div>
    <div id="body" class="container mx-auto flex-1">
      <jsp:doBody/>
    </div>
    <div id="pagefooter">
      <jsp:invoke fragment="footer" />
    </div>
  </body>
</html>