#include <string>

enum ETokenType
{
    OPERATOR,
    NUMBER,
    VARIABLE,
    TRIG_FUNCTION,
    UNDEFINED
};

class CNode
{
private:
    ETokenType EType;
    std::string value;
    int expectedChildrenCount;
    CNode **children;
    int actualChildrenCount;

    int getExpectedChildrenCount();
    bool isVariable(const std::string &token);
    bool isOperator(const std::string &token);
    bool isTrigFunction(const std::string &token);
    bool isDigit(char c);
    bool isDigitOrLetter(char c);
    bool isLetter(char c);
    void clearChildren();
    std::string clearToken(const std::string &token, bool &wasCharacterIgnored);

public:
    CNode();
    CNode(ETokenType EType, std::string &value);
    ~CNode();
    CNode(const CNode &other);
    void buildFromFormula(const std::string &formula, int &offset,
                          bool &wasCharacterIgnored, bool &wasArgumentAdded,
                          bool &wasInvalidTokenIgnored);
    std::string getPrefix() const;
    void collectVariables(std::string **vars, int &count, int &capacity);
    double calculate(std::string *vars, double *values, int count, bool &divisionByZero);
    bool replaceLeaf(const CNode &other);
    bool isLeaf();
    std::string getNextToken(const std::string &formula, int &formulaIdx);
    bool isNumber(const std::string &token);
    int stringToNumber(const std::string &text);
};
