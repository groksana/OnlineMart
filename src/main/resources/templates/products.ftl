<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Products</title>

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
                <a th:class="${addProductState == 'disabled'} ? 'nav-link disabled' : 'nav-link'" th:href="${addProductState == 'active'} ? '/newproduct' : null">Add Product</a>
            </li>
        </ul>
        <form class="form-inline mx-auto" action="/products/search">
            <input class="form-control mr-sm-2" type="text" id="searchtext" name="name" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit" onclick="myFunction()">Search</button>
        </form>
        <script> function myFunction()
        {
            var search = document.getElementById("searchtext").value;
            window.location = '/products/search?name='+search;
        }
        </script>
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
            <div class="col-md-4" th:each="product : ${products}">
                <div class="boximg">
                    <img th:src=${product.picturePath}
                                 width="180" height="236">
                </div>
                <h2 th:text="${product.name}">Name</h2>
                <p th:text="'Price : $' + ${product.price}">Price</p>
                <p><a class="btn btn-secondary" th:href="'/product/' + ${product.id}" role="button">View details
                    &raquo;</a></p>
            </div>
        </div>
        <hr>
    </div>

</main>

<div class="navbar-fixed-bottom row-fluid">
    <div class="navbar-inner">
        <footer class="container">
            <p align="center">&copy; Company 2018</p>
        </footer>
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
