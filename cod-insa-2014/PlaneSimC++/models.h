#ifndef MODELS_H
#define MODELS_H

struct Coord
{
    double x, y;
};

struct Base;

struct Plane
{
    enum State { IDLE, GOING_TO, FOLLOWING, ATTACKING, LANDING, AT_AIRPORT, DEAD, ENEMY };

    struct Type
    {

        int id;
        double
            firingRange,
            radarRange,
            fullHealth,
            holdCapacity,
            tankCapacity,
            fuelConsumptionPerDistanceUnit,
            radius,
            timeToBuild;

        Type(int a, double b, double c, double d, double e, double f, double g, double h, double i)
            :   id(a),
                firingRange(b),
                radarRange(c),
                fullHealth(d),
                holdCapacity(e),
                tankCapacity(f),
                fuelConsumptionPerDistanceUnit(g),
                radius(h),
                timeToBuild(i) {}

        static Type *MILITARY;
        static Type *COMMERCIAL;

        static Type *types[2];

    };

    int id;
    Coord pos;
    State state;
    Base *curBase;
    double health;
    double militaryInHold;
    double fuelInHold;
    double holdCapacity;
    double fuelInTank;
    Type *type;
};

Plane::Type *Plane::Type::MILITARY = new Plane::Type(0, 0.7, 0.7, 100, 10, 10, 1, .03, 15);

Plane::Type *Plane::Type::COMMERCIAL = new Plane::Type(
    1,
    0,
    Plane::Type::MILITARY->radarRange,
    Plane::Type::MILITARY->fullHealth*2,
    Plane::Type::MILITARY->holdCapacity*5,
    Plane::Type::MILITARY->tankCapacity*4,
    Plane::Type::MILITARY->fuelConsumptionPerDistanceUnit*3,
    Plane::Type::MILITARY->radius*2,
    15
);

Plane::Type *Plane::Type::types[] = {Plane::Type::MILITARY, Plane::Type::COMMERCIAL};

struct Base;

struct ProgressAxis
{
    Base *base1;
    Base *base2;
    double length;
    double ratio1 = 0, ratio2 = 0;

};

struct Base
{
    Coord position;
    std::list<Plane> planes;

    std::list<ProgressAxis> axes;

    double militaryGarrison;
    double fuelInStock;
};

struct Country
{
    struct Request
    {
        int id;
        double timeBeforePlaneBuilt;
        Plane::Type *requestedType;
    };
    int id;
    std::map<int, Request> productionLine;
};

#endif /* MODELS_H */
