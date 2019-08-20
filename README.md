# MVVM_Retrofit
MVVM sample working with recyclerview and glide as image loader

Objective of this app is to build MVVM sample working with recyclerview and glide as image loader
To attain above features following libraries is used: -Retrofit2 -OkHttp3 -RecyclerView -ConstraintLayout

Application Flow

Application queries list of photos from www.unsplash.com
Responses are in form json where url of images are given.
HTTP GET connection for those images in form of bytestream
Response from above HTTP requests are displayed in recyclerview.
Images are selected are opened in full screen using Fragments.

Deep Dive
Application uses Builder pattern, Singleton Pattern and Mediator pattern.

To get requests from url application uses Retrofit2 library. Retrofit2 is faster then other HTTP libraries and provides easy implementation and readable response using POJO classes(Url, ImageSource).

Here we have loaded for unlimited number of images which are asynchronous with help of pagination. This can be scaled up to more then that. Since application is not aware how many images should be shown.
