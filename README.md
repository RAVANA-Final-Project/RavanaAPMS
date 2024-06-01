<title>Image Collage</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
            font-family: Arial, sans-serif;
        }
        .collage {
            display: grid;
            gap: 10px;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            grid-auto-rows: 150px;
        }
        .collage img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .collage .large {
            grid-column: span 2;
            grid-row: span 2;
        }
        .collage .wide {
            grid-column: span 2;
        }
        .collage .tall {
            grid-row: span 2;
        }
    </style>
</head>
<body>
    <div class="collage">
        <img src="path/to/image1.jpg" alt="Image 1" class="large">
        <img src="path/to/image2.jpg" alt="Image 2">
        <img src="path/to/image3.jpg" alt="Image 3" class="tall">
        <img src="path/to/image4.jpg" alt="Image 4">
        <img src="path/to/image5.jpg" alt="Image 5" class="wide">
        <img src="path/to/image6.jpg" alt="Image 6">
        <img src="path/to/image7.jpg" alt="Image 7">
        <img src="path/to/image8.jpg" alt="Image 8">
    </div>
