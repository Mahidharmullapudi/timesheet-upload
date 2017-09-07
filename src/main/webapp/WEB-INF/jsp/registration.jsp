<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="images/favicon.ico">
    <title>TechNumen Registration</title>
    <!-- Bootstrap core CSS -->
    <link href="css/external/bootstrap.min.css" rel="stylesheet">
    <!--Fonts-->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:300,300i,400,400i,500,500i,600,600i,700,700i"
          rel="stylesheet">
    <!-- Icons -->
    <link href="css/external/font-awesome.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">
</head>
<body>
<div class="container" id="wrapper">
    <main class="col-md-12">
        <section class="row">
            <div class="col-sm-12">
                <section class="row">
                    <div class="form-signin">
                        <br><img src="images/technumen-logo.png">
                        <hr>
                    </div>
                    <div class="col-md-12">
                        <h4 class="subheading"> Welcome to TechNumen Inc., - Timesheet App. Please create your Account
                            to continue. </h4>
                        <hr>
                        <c:if test="${not empty msg}">
                            <div class="col-md-12">
                                <div class="alert alert-${css} alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert"
                                            aria-label="Close">
                                        <span aria-hidden="true">X</span>
                                    </button>
                                    <i class="fa fa-info-circle faa-flash animated"></i><strong> ${msg}</strong>
                                </div>
                            </div>
                        </c:if>

                        <spring:url value="/registration" var="registrationActionUrl"/>
                        <form:form method="POST" modelAttribute="employee"
                                   action="${registrationActionUrl}">

                            <div class="row">
                                <spring:bind path="employeeFirstName">
                                    <div class="form-group required col-md-6 ${status.error ? 'has-danger' : ''}">
                                        <label class="form-control-label" for="contactFName">First Name</label>
                                        <div class="inputGroupContainer">
                                            <form:input path="employeeFirstName" name="contactFName"
                                                        id="contactFName" maxlength="50"
                                                        placeholder="Enter First Name" data-toggle="tooltip"
                                                        data-animation="false"
                                                        class="form-control ${status.error ? 'form-control-danger' : ''}"
                                                        required="required"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="employeeFirstName"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="employeeLastName">
                                    <div class="form-group required col-md-6 ${status.error ? 'has-danger' : ''}">
                                        <label class="form-control-label" for="contactLname">Last Name</label>
                                        <div class="inputGroupContainer">
                                            <form:input path="employeeLastName"
                                                        name="contactLname" id="contactLname"
                                                        maxlength="50"
                                                        placeholder="Enter Last Name" data-toggle="tooltip"
                                                        data-animation="false"
                                                        class="form-control ${status.error ? 'form-control-danger' : ''}"
                                                        required="required"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="employeeLastName"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>
                            </div>

                            <div class="row">
                                <spring:bind path="employeeEmailId">
                                    <div class="form-group required col-md-12 ${status.error ? 'has-danger' : ''}">
                                        <label for="emailId" class="form-control-label">Email</label>
                                        <div class="inputGroupContainer">
                                            <form:input path="employeeEmailId" maxlength="50" type="email" id="emailId"
                                                        placeholder="Email" data-toggle="tooltip" data-animation="false"
                                                        class="form-control ${status.error ? 'form-control-danger' : ''}"
                                                        required="required"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="employeeEmailId"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>
                            </div>

                            <div class="row">
                                <spring:bind path="empPassword">
                                    <div class="form-group required col-md-6 ${status.error ? 'has-danger' : ''}">
                                        <label class="form-control-label" for="employeeTitle">Password</label>
                                        <div class="inputGroupContainer">
                                            <form:password path="empPassword"
                                                           name="password" id="password" maxlength="25"
                                                           placeholder="Password" data-toggle="tooltip"
                                                           data-animation="false" data-placement="left"
                                                           title="Password can be 6 to 25 characters. Should consist of atleast one uppercase character, digit and special symbol (@#$%)"
                                                           class="form-control ${status.error ? 'form-control-danger' : ''}"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="empPassword"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="empPassword2">
                                    <div class="form-group required col-md-6 ${status.error ? 'has-danger' : ''}">
                                        <label class="form-control-label" for="employeePhone">Re-enter Password</label>
                                        <div class="inputGroupContainer">
                                            <form:password path="empPassword2"
                                                           name="password2" id="password2" maxlength="25"
                                                           placeholder="Re-enter Password" data-toggle="tooltip"
                                                           data-animation="false" data-placement="right"
                                                           title="Please re-enter the same password."
                                                           class="form-control ${status.error ? 'form-control-danger' : ''}"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="empPassword2"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>
                            </div>
                            <hr>
                            <div class="row">
                                <spring:bind path="employeeTitle">
                                    <div class="form-group col-md-6 ${status.error ? 'has-danger' : ''}">
                                        <label class="form-control-label" for="employeeTitle">Role Title</label>
                                        <div class="inputGroupContainer">
                                            <form:input path="employeeTitle"
                                                        name="employeeTitle" id="employeeTitle" maxlength="50"
                                                        placeholder="Enter Role Title" data-toggle="tooltip"
                                                        data-animation="false"
                                                        class="form-control ${status.error ? 'form-control-danger' : ''}"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="employeeTitle"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="employeePhone">
                                    <div class="form-group col-md-6 ${status.error ? 'has-danger' : ''}">
                                        <label class="form-control-label" for="employeePhone">Phone Number</label>
                                        <div class="inputGroupContainer">
                                            <form:input path="employeePhone"
                                                        name="employeePhone" id="employeePhone" maxlength="10"
                                                        placeholder="(xxx) xxx-xxxx" data-toggle="tooltip"
                                                        data-animation="false"
                                                        class="form-control phone_us ${status.error ? 'form-control-danger' : ''}"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="employeePhone"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>
                            </div>

                            <div class="row">
                                <spring:bind path="clientName">
                                    <div class="form-group col-6 ${status.error ? 'has-danger' : ''} snears-vendor-info">
                                        <label class="form-control-label" for="clientName">Client Name</label>
                                        <div class="inputGroupContainer">
                                            <form:input path="clientName" name="clientName"
                                                        maxlength="50"
                                                        placeholder="Client Name" data-toggle="tooltip"
                                                        data-animation="false"
                                                        class="form-control ${status.error ? 'form-control-danger' : ''}"
                                                        id="clientName"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="clientName"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="clientAddress">
                                    <div class="form-group col-md-6 ${status.error ? 'has-danger' : ''}">
                                        <label class="form-control-label" for="clientAddress">Client Address</label>
                                        <div class="inputGroupContainer">
                                            <form:input path="clientAddress"
                                                        maxlength="100" name="clientAddress" id="clientAddress"
                                                        placeholder="Client Address" data-toggle="tooltip"
                                                        data-animation="false"
                                                        class="form-control ${status.error ? 'form-control-danger' : ''}"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="clientAddress"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>
                            </div>

                            <div class="row">
                                <spring:bind path="clientCity">
                                    <div class="form-group col-md-6 ${status.error ? 'has-danger' : ''}">
                                        <label class="form-control-label" for="clientCity">City</label>
                                        <div class="inputGroupContainer">
                                            <form:input path="clientCity" maxlength="50" name="clientCity"
                                                        id="clientCity"
                                                        placeholder="Enter City" data-toggle="tooltip"
                                                        data-animation="false"
                                                        class="form-control ${status.error ? 'form-control-danger' : ''}"/>
                                            <div class="form-control-feedback">
                                                <form:errors
                                                        path="clientCity"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="clientState">
                                    <div class="form-group col-md-3">
                                        <label class="form-control-label" for="clientState">State</label>
                                        <div class="inputGroupContainer">
                                            <form:input
                                                    path="clientState"
                                                    name="clientState" id="clientState" maxlength="2"
                                                    placeholder="Enter State"
                                                    data-toggle="tooltip" data-animation="false"
                                                    class="form-control ${status.error ? 'form-control-danger' : ''}"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="clientState"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="clientZip">
                                    <div class="form-group col-md-3">
                                        <label class="form-control-label" for="clientZip">Zip</label>
                                        <div class="inputGroupContainer">
                                            <form:input path="clientZip"
                                                        name="clientZip" id="clientZip" maxlength="5"
                                                        placeholder="Enter Zip Code" data-toggle="tooltip"
                                                        data-animation="false"
                                                        class="form-control ${status.error ? 'form-control-danger' : ''}"/>
                                            <div class="form-control-feedback">
                                                <form:errors path="clientZip"/>
                                            </div>
                                        </div>
                                    </div>
                                </spring:bind>
                            </div>
                            <br>

                            <p class="text-center">By clicking on Register, you agree to
                                <a href="#">Terms & Conditions/Privacy Policy</a>.
                            </p>

                            <div class="form-signin">
                                <button class="btn btn-lg btn-success btn-block" type="submit">Register</button>
                            </div>

                        </form:form>
                        <hr>
                        <div class="form-signin">
                            <a href="login.html">Login into your Existing TechNumen account</a>
                        </div>
                    </div>
                </section>
            </div>
        </section>
    </main>
</div>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="js/external/jquery-3.2.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
        integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
        crossorigin="anonymous"></script>
<script src="js/external/popper.min.js"></script>
<script src="js/external/bootstrap.min.js"></script>
<script src="js/external/jquery.mask.min.js"></script>

<script>
    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $('.phone_us').mask('(000)000-0000');
    })
</script>
</body>
</html>
