package demo

class Robot {
    def type ,height,width
    //第一个形参为Map
    def access(location,weight,fragile) {
        println "Recieved  fragile ? $fragile,weight $weight,location $location "
    }
}

//构造函数实参为键值对
robot =new Robot(type:'arm',width:10,height:40)
println "$robot.type ,$robot.width , $robot.height"
//由于第一个形参为map,实参可以直接为键值对
robot.access(x:30,y:20,z:10,50,true)
//可以修改参数顺序
robot.access(50,true,x:30,y:20,z:10)
//可选形参，只需将形参赋予一个缺省值
def log(x,base=10) {
    Math.log(x)/Math.log(base)
}

println log(1024)
println log(1024,10)
println log(1024,2)
//可选形参还有一种用法,末尾的实参同一类型，收集起来赋值给形参

def task(name,String[] details) {
    println "$name - $details"
}

task 'Call' ,'123-456-7890'

task 'Call' ,'123-456-7890','231-546-0987'

task "Cehck mail"

//多赋值
def name1="Thomson"
def name2="Thompson"

(name1,name2)=[name2,name1]    //swap value
println "$name1 and $name2"

def (first ,second ,third) =['Tome','Jerry']
println "$first ,$second,$third"

//操作符重载

class ComplexNumber {
    def real ,imaginary
    def plus (other) {
       new ComplexNumber(real:real+other.real,imaginary:imaginary+other.imaginary)
    }

    String toString() {"$real ${imaginary>0?'+':''} ${imaginary}i"}
}

c1=new ComplexNumber(real:1,imaginary:2)
c2=new ComplexNumber(real:4,imaginary:1)
println c1+c2