package demo

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
