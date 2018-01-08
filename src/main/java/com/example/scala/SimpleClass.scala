package com.example.scala

import java.io.IOException

import scala.beans.{BeanProperty, BooleanBeanProperty}

class SimpleClass(name: String, val acc: String, @BeanProperty var mutable: String) {
  val foo = "foo"
  var bar = "bar"
  @BeanProperty
  val fooBean = "foobean"
  @BeanProperty
  var barBean = "barbean"
  @BeanProperty
  var baBean = "barbean"
  @BooleanBeanProperty
  var awesome = true

  def dangerFoo() = {
    throw new IOException("SURPRISE!")
  }

  @throws(classOf[IOException])
  def dangerBar() = {
    throw new IOException("NO SURPRISE!")
  }
}

//class SimpleClass(acc_: String) {
//  val acc = acc_
//}
