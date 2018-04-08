<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Login</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Custom styles for this template -->
    <link rel="StyleSheet" href="css/signin.css" TYPE="text/css">
</head>
<body class="text-center">
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
                <a class="nav-link disabled" href="#">Add Product</a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="nav-item">
                <a class="nav-link" href="#"><span class="fa fa-user"></span>Your Account</a>
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
</main>
<form class="form-signin" action="/login" method="POST">
    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
    <label for="login" class="sr-only">Email address</label>
    <input type="text" name="login" id="login" class="form-control" placeholder="login" required autofocus>
    <label for="password" class="sr-only">Password</label>
    <input type="password" name="password" id="password" class="form-control" placeholder="password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit" name="ok">Sign in</button>
    <p class="mt-5 mb-3 text-muted">&copy; Company 2018</p>
</form>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>