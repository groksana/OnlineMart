<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>User</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="#">OnlineMart</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
            aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/products">Products <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a th:class="${addProductState == 'disabled'} ? 'nav-link disabled' : 'nav-link'"
                   th:href="${addProductState == 'active'} ? '/newproduct' : null">Add Product</a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="nav-item">
                <a class="nav-link" href="/user"><span class="fa fa-user"></span>Your Account</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cart"><span class="fa fa-shopping-cart"></span>Cart</a>
            </li>
        </ul>
    </div>
</nav>

<main role="main">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron clearfix">
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-xs-12 col-sm-6 col-lg-8">
                <div class="container">
                    <h2 th:text="${user.nickname}">User</h2>
                    <p>an <b th:text="${user.role}"> Employee</b></p>
                </div>
                <hr>
                <ul class="container details">
                    <li><p><span class="fa fa-envelope one" style="width:50px;"></span><i th:text="${user.login}">User</i>
                    </p></li>
                </ul>
                <hr>
                <div class="col-sm-5 col-xs-6 tital ">
                    <a class="btn btn-primary btn-md" href="/products" role="button">Back To Products</a>   <a class="btn btn-primary btn-md" href="/logout" role="button">LogOut</a>
                </div>
            </div>
        </div>
    </div>
</main>


<div class="navbar-fixed-bottom row-fluid">
    <div class="navbar-inner">
        <div class="container">
            <footer class="container">
                <p style="position: fixed; bottom: 0; width:100%; text-align: center">&copy; Company 2018</p>
            </footer>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>
