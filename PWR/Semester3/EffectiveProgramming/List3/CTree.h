
#include <string>
#include "CNode.h"

class CTree
{
private:
    CNode *root;

    bool wasCharacterIgnored;
    bool wasArgumentAdded;
    bool wereExtraArgumentsIgnored;
    bool wasInvalidTokenIgnored;

public:
    CTree();
    CTree(const CTree &other);
    ~CTree();

    CTree &operator=(const CTree &other);
    CTree operator+(const CTree &other) const;

    void enter(const std::string &formula);

    std::string print() const;
    std::string vars();

    bool comp(const std::string &arguments, double &result, bool &divisionByZero);

    bool getWasCharacterIgnored() const;
    bool getWasArgumentAdded() const;
    bool getWereExtraArgumentsIgnored() const;
    bool getWasInvalidTokenIgnored() const;
};