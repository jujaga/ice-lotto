## JPA

The JPA model in the application is based on the first two articles in the
tutorial at
[http://www.petrikainulainen.net/spring-data-jpa-tutorial/](http://www.petrikainulainen.net/spring-data-jpa-tutorial/).

I'm not entirely sold on the "DTO" pattern, so that has the potential to be
ripped out altogether. But I can also see how it would be beneficial for some
asynchronous operations. So maybe not.

What I do know is that I don't like the services returning direct instances
of the entities. That leaves simply returning `null` when an error occurs.
So all of the services are likely to be refactored to return entity instances
wrapped in an Optional (see the Guava library).

## Design Plan

My plan is to take advantage of Spring 4.0's new support for STOMP/Websockets.
The idea being that when a drawing is held, anyone watching the drawing will
receiving updates without having to refresh.

All of the UI ("View") code will be in JavaScript. All actual operations,
e.g selecting winners or retrieving information from the GW2 web API, will be
done server side. Thereby reducing the possibility for tampering.

Speaking of selecting winners, I have already worked out that algorithm at
[http://jsfiddle.net/jsumners/22XAU/](http://jsfiddle.net/jsumners/22XAU/). I'll
be rewriting it with a secure random providing the actual choices, though. And
I'll also be adding persistence entities for recording the order of entrants
in each drawing (i.e. the list after expansion and shuffling has occured).

## Project configuration

Spring profiles are used to distinguish the "dev" and "prod" environments. When
launching the application, if "`-Dapplication.profile=dev`" is not supplied
then the production, "prod", profile will be used.

Each profile looks for a properties file named "`application-env.properties`",
where "env" is either "dev" or "prod", e.g. "`application-dev.properties`". A
sample properties file is included, and is sure to be expanded/optomized. So,
the first step to running this application is to create the appropriate file
in the `src/main/resources/` directory.

This project also uses JavaConfig instead of a bunch of annoying XML.

## Current Status

At the moment, I'm still building out an intial design for administering
the application. My general outline is:

  1. Develop a view template for the administrator to manage the items in a drawing
  2. Develop a view template for the administrator to add new drawings
  3. Derive the non-admin drawings view from #2
  4. Add authorization and user roles
  5. Add views for inspecting previous drawings and the details behind them

Once the administrator stuff is mostly worked out this thing should start
progressing much quicker. Most of the application is in the administration.

## Code Style

I have one, I promise.

First rule: spaces for tabs and a tab equals two spaces
Second rule: Unix line endings

For Java, it's very similar to the
[Google Java Style](http://google-styleguide.googlecode.com/svn/trunk/javaguide.html)
with a few exceptions (e.g. starting braces for methods being on a line by
themselves when the method declaration gets "too long").

For JavaScript, if it doesn't pass JSHint's default rules (mostly; it'll be
clarrified later) then it's out of the question. But the basics are:

  1. Use the module pattern, i.e. encapsulate everything in a closure and
  create as few new entries in the global space as possible.
  2. "use strict";
  3. Prefer double quotes unless it would make escaping annoying, then use
  single quotes
  4. Use a single `var` statement at the top of functions and illustrate the
  target "types"

An example:

```javascript
// `fooBar` is in the global space in this example
var fooBar = (function() {
  "use strict";

  var obj = {},
      bar = function(){};

  obj.bar = bar;

  obj.hello = function() {
    console.log("hello world");
  }

  obj.answer = 42;

  bar = function() {
    console.log("answer = " + obj.answer);
  };

  return obj;
}());

fooBar.hello(); // "hello world"
fooBar.bar(); // "answer = 42"
```