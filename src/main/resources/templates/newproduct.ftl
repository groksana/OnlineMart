<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Product</title>

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
        <form action="/newproduct" method="POST">
            <fieldset>
                <!--<legend>Please add information by new product</legend>-->
                <h4 class="heading"><strong>New </strong> Product <span></span></h4>
                <hr>
                <div class="form-group">
                    <label class="col-sm-3 control-label" for="product-input">Product Name</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="name" id="product-input"
                               placeholder="Input Product Name">
                    </div>
                </div>
                <div class="form-group">
                    <label for="image-input" class="col-sm-3 control-label">Image Path</label>
                    <div class="col-sm-9">
                        <input class="form-control" type="text" name="image" value="" placeholder="Input Image Path"
                               id="image-input">
                    </div>
                </div>
                <div class="form-group">
                    <label for="price-input" class="col-sm-3 control-label">Price</label>
                    <div class="col-sm-9">
                        <input class="form-control" type="text" name="price" value="" placeholder="Input Price"
                               id="price-input">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description-input" class="col-sm-3 control-label">Description</label>
                    <div class="col-sm-9">
                    <textarea class="form-control" name="description" placeholder="Input Description" id="description-input"
                              rows="3"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-9">
                        <h6 style="color: red" th:text="${message}">Message</h6>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-3 col-sm-9">
                        <button type="submit" class="btn btn-success" name="ok">Add Product</button>
                    </div>
                </div>
            </fieldset>
        </form>
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
