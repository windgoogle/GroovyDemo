package demo

class Car {
    private miles=0
    final year

    Car(theYear) {year=theYear}

    def drive(dist) {if(dist>0) dist+=miles}

    def getMiles () {
        println "getMiles called"
        miles     //不需要return ,默认最后一行的值为返回值
    }

    private void setMiles(miles) {
        throw new IllegalAccessException("you are not allowed to change miles")
    }



}

Car car=new Car(2018)

println "Year: $car.year"
println "Miles : $car.miles"
println "driving"
car.drive(10)
println "Miles : $car.miles"

try{
    print "Can I set  the year ?"
    car.year=1900
}catch (groovy.lang.ReadOnlyPropertyException ex) {
    println ex.getMessage()
}

try{
    print "Can I set  the year ?"
    car.miles=12
}catch (IllegalAccessException ex) {
    println ex.getMessage()
}

//println "Setting miles "
//car.miles=25
//println "Miles : $car.miles"