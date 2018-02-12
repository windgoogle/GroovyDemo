package demo

import groovy.transform.Canonical

//集合


def range1 =0..4    //定义一个集合
println range1.class
println range1 instanceof List

def range2=["java","ruby","groovy"]
println range2.class                    //java.util.ArrayList
println range2 instanceof List

range2.add("Python")
range2 << "Smalltalk"
range2[5] = "Perl"

for(i in 0..range2.size()){
    println(range2[i])
}

//魔法方法

//join count  *   分布操作符
def numbers=[1,2,3,4]
assert numbers + 5 ==[1,2,3,4,5]   //断言
assert numbers -[2,3]==[1,4 ] ;println numbers       //加和减并不改变集合变量本身
assert numbers.join(",")=="1,2,3,4"    //用逗号拼接成字符串
assert [1,2,3,4,3].count(3) ==2      //计算有几个3

assert ["JAVA","GROOVY"] == ["Java","Groovy"]*.toUpperCase()


//映射

def hash =[name:"Andy","VPN-#":45]

println hash.get("name")
println hash.get("VPN-#")

hash.put("id",23)     //java  put方法
println hash.get("id")


//闭包
//闭包是可执行的代码块,不需要名成，可以在定义后执行

def acoll=["Groovy","Java","Ruby"]
acoll.each {     //花括号内为闭包  ，使用闭包简化代码，这里简化了迭代的代码
    println it    //it 是个关键字
}

acoll.each {value ->     //value 取代了关键字it
    println value
}
//闭包简化映射迭代代码
hash.each {k,v ->
    println "${k}:${v}"
}

//接受参数的闭包

def excite ={ world ->      //定义个方法，这个方法体是带参数的闭包
    println "${world} !!"
    println world         // 也可以
}

excite("hello world !")
excite.call ("hello groovy")


//dump 和inpsect 方法
str = 'hello'
println str
println str.dump()
println str.inspect()

//循环

0.upto(2) {print it}
println ""

3.times {print 'ho '}
println "Merry Groovy"

//执行svn 命令
println "svn help".execute().text

println "tasklist".execute().text

//安全导航操作符

def foo(str) {
    //if(str!=null) str.reverse()
    str?.reverse()  //等效上述语句
}

println foo("evil")
println foo(null)

//异常处理

//groovy 并不特别处理异常,要处理和java一样try catch



//对java5特性的支持
//自动装箱
int val=5
println val.getClass().name

//for-each 循环
String greetings=["Hello","hi","Howdy"]
for(String greet :greetings) {
    println greet
}

//另一种风格的for-each循环
for (greet in greetings) {
    println greet
}
//enum
enum CoffeeSize {SHORT,SMALL,MEDIUM,LARGE,MUG}
def orderCoffee (size) {
    print "Coffee oeder recieved for  size  $size : "
    switch (size) {
      case [CoffeeSize.SHORT,CoffeeSize.SMALL] :
          println "you are health conscious"
          break
      case CoffeeSize.MEDIUM..CoffeeSize.LARGE :
          println "you  gotta be a programmer"
            break
      case CoffeeSize.MUG :
          println "you should try Coffeine IV"
            break

    }
}

orderCoffee(CoffeeSize.SMALL)
orderCoffee(CoffeeSize.LARGE)
orderCoffee(CoffeeSize.MUG)

for (size in CoffeeSize.values()) {
    println "$size"
}

//带方法和构造函数的枚举
enum Methodlogies {
    Evo(5),
    XP(21),
    Scrum(3);

    final int daysInIteration
    Methodlogies(days) {daysInIteration=days}

    def iterationDetails () {
        println "${this} recommends you $daysInIteration days for iteration"
    }

}

for (methodlogy in Methodlogies){
    methodlogy.iterationDetails()
}

//变长参数 (varargs)
//以下两个方法均接受数目不等的实参
def receivedVarArgs (int a,int ...b) {
    println "You passed $a and $b"
}


def receivedArray (int a,int[] b) {
    println "You passed $a and $b"
}

receivedVarArgs(1,2,3,4,5)
receivedArray(1,2,3,4,5)

//int values=[2,3,4,5]
//receivedVarArgs(1,values)
//receivedVarArgs(1,[2,3,4,5] as int[])
//注解

//静态导入
import static Math.random as rand
import  groovy.lang.ExpandoMetaClass as EMC
double value=rand()
def metaClass=new EMC(Integer)
assert metaClass.getClass().name=='groovy.lang.ExpandoMetaClass'


//泛型
//groovy 的变换
// demo @Canonical
import groovy.transform.*
//toString()
@Canonical (excludes = "lastName,age")
class Person {
    String firstName
    String lastName
    int age
}

def sara=new Person(firstName:"Sara",lastName:"Walker",age:49)
println sara


// demo @Delegate

class Worker {
    def work () { println "get work done !" }
    def analyze () {println "analyze ...."}
    def writeReport() {println "get report written !"}
}

class Expert {
   def analyze () {println "expert analysis ...."}
}

class Manager {
    @Delegate Expert expert=new Expert()
    @Delegate Worker worker =new Worker()
}

def bernie=new Manager()
bernie.analyze()
bernie.work()
bernie.writeReport()


//demo @Immutable
@Immutable
class CreditCard {
    String cardNumber
    int creditLimit
}

println new CreditCard("400-1111-2222-3333",1000)

//demmo  @Lazy
class Heavy {
    def size=10
    Heavy() {println "Creating Heavy with  $size "}
}

class AsNeeded {
    def value
    @Lazy Heavy heavy1=new Heavy()
    @Lazy Heavy heavy2=new Heavy(size:value)

    AsNeeded(){println "Create AsNeeded"}
}

def asNeeded=new AsNeeded(value:1000);

println asNeeded.heavy1.size
println asNeeded.heavy1.size
println asNeeded.heavy2.size

//demo @Newify

@Newify([Person,CreditCard])
def fluentCreate() {
    println Person.new(firstName: "John",lastName: "Doe",age: 20)
    println Person(firstName: "John",lastName: "Doe",age: 20)
    println CreditCard("1234-5678-1234-5678",2000)
}

fluentCreate()


//demo @Singleton

@Singleton(lazy=true)
class TheUnique{
   //private TheUnique() {println "Instance Created"}
    def hello () {println "hello"}
}

println "Accessing TheUnique"
println TheUnique.instance.hello()
//println TheUnique.instance.hello()

class A {
  boolean equals (other) {
      println "equals called"
      false
  }
}

class B implements Comparable{
    boolean equals (other) {
        println "equals called"
        false
    }

    @Override
    int compareTo(other) {
        println "compareTo called"
        0
    }
}
//  ==并不完全是equals
new A()==new A()
new B()==new B()