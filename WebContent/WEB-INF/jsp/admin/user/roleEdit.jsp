<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
var userId = <c:out value="${user.userid}" />;
       
       
        $(document).ready(function() {
        	var realValues = [];
            var textValues = [];
            var returnData = {};
          
            var userRole = {};
            var roleList ={};
        	 $(function() {
                 $("#MoveRight,#MoveLeft").click(function(event) {
                     var id = $(event.target).attr("id");
                     var selectFrom = id == "MoveRight" ? "#selectLeft" : "#selectRight";
                     var moveTo = id == "MoveRight" ? "#selectRight" : "#selectLeft";
      
                     var selectedItems = $(selectFrom + " :selected").toArray();
                     $(moveTo).append(selectedItems);
                     selectedItems.remove;
                 });
             });
        	
        	
            
            
        $('#roleForm').submit(function() {
        	
        
	        $('#selectRight :enabled').each(function(i, selected) {
	        	
	        	userRole.roleid = parseInt($(selected).val());
	            userRole.rolename = $(selected).text();
	            returnData[userRole.roleid] = userRole.rolename;
	            	      
	        });
      
               
        $.each(returnData, function(key, value) { 
                   
        var jsonText = JSON.stringify(returnData);
      //  console.debug("json: "+ jsonText);
        $.post('<spring:url value="/admin/users/id/'+userId + '/roleUpdate"></spring:url>', returnData);
        return false;
        
    
        
        });
        return false;
        });
        });        
    </script>


<div class="grid_8 prefix_4">
	<div class="box shadow">
		<h1>Role Editing</h1>

		<h2>You are editing user:</h2>
		<p>
			<c:out value="${user.firstName}" />
			<c:out value="${user.lastName}" />
		</p>


		<form id="roleForm" method="POST" action="">
			Available roles <select id="selectLeft" name="selectLeft"
				multiple="multiple">
				<c:forEach var="role" items="${availableRoles}">
					<option value="<c:out value="${role.roleid}" />">
						<c:out value="${role.rolename}" />
					</option>
				</c:forEach>
			</select> <input id="MoveRight" type="button" value=" >> " /> <input
				id="MoveLeft" type="button" value=" << " /> <select
				id="selectRight" name="selectRight" multiple="multiple">
				<c:forEach var="role" items="${currentRoles}">
					<option value="<c:out value="${role.roleid}" />">
						<c:out value="${role.rolename}" />
					</option>
				</c:forEach>
			</select> User roles <input id="userId" name="userId" type="hidden"
				value="<c:out value="${user.userid}" />"></input> <input
				type="submit" value="Update" />
		</form>
		<span id="status">Add the required roles</span>


	</div>

</div>

