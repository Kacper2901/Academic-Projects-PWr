#include "CTree.h"
#include <sstream>

CTree::CTree() : root(NULL),
                 wasCharacterIgnored(false),
                 wasArgumentAdded(false),
                 wereExtraArgumentsIgnored(false),
                 wasInvalidTokenIgnored(false)
{
}

CTree::CTree(const CTree &other) : root(NULL),
                                   wasCharacterIgnored(other.wasCharacterIgnored),
                                   wasArgumentAdded(other.wasArgumentAdded),
                                   wereExtraArgumentsIgnored(other.wereExtraArgumentsIgnored),
                                   wasInvalidTokenIgnored(other.wasInvalidTokenIgnored)
{
    if (other.root != NULL)
    {
        root = new CNode(*other.root);
    }
}

CTree::~CTree()
{
    if (root != NULL)
    {
        delete root;
    }
}

CTree &CTree::operator=(const CTree &other)
{
    if (this != &other)
    {
        CNode *newRoot = NULL;

        if (other.root != NULL)
        {
            newRoot = new CNode(*other.root);
        }

        if (root != NULL)
        {
            delete root;
        }

        root = newRoot;

        wasCharacterIgnored = other.wasCharacterIgnored;
        wasArgumentAdded = other.wasArgumentAdded;
        wereExtraArgumentsIgnored = other.wereExtraArgumentsIgnored;
        wasInvalidTokenIgnored = other.wasInvalidTokenIgnored;
    }

    return *this;
}

CTree CTree::operator+(const CTree &other) const
{
    CTree result(*this);

    if (other.root == NULL)
    {
        return result;
    }

    if (result.root == NULL || result.root->isLeaf())
    {
        CNode *newRoot = new CNode(*other.root);

        if (result.root != NULL)
        {
            delete result.root;
        }

        result.root = newRoot;
    }
    else
    {
        result.root->replaceLeaf(*other.root);
    }

    return result;
}

void CTree::enter(const std::string &formula)
{
    if (root != NULL)
    {
        delete root;
    }

    root = new CNode();

    wasCharacterIgnored = false;
    wasArgumentAdded = false;
    wereExtraArgumentsIgnored = false;
    wasInvalidTokenIgnored = false;

    int formulaIdx = 0;

    root->buildFromFormula(formula, formulaIdx, wasCharacterIgnored,
                           wasArgumentAdded, wasInvalidTokenIgnored);

    if (!root->getNextToken(formula, formulaIdx).empty())
        wereExtraArgumentsIgnored = true;
}

std::string CTree::print() const
{
    if (root == NULL)
    {
        return "";
    }

    return root->getPrefix();
}

std::string CTree::vars()
{
    if (root == NULL)
    {
        return "";
    }

    std::string *variables = NULL;
    int count = 0;
    int capacity = 0;

    root->collectVariables(&variables, count, capacity);

    std::stringstream result;

    for (int i = 0; i < count; i++)
    {
        if (i > 0)
        {
            result << " ";
        }

        result << variables[i];
    }

    if (variables != NULL)
    {
        delete[] variables;
    }

    return result.str();
}

bool CTree::comp(const std::string &arguments, double &result, bool &divisionByZero)
{
    divisionByZero = false;

    if (root == NULL)
    {
        return false;
    }

    std::string *variables = NULL;
    int count = 0;
    int capacity = 0;

    root->collectVariables(&variables, count, capacity);

    double *values = NULL;

    if (count > 0)
    {
        values = new double[count];
    }

    int argumentIdx = 0;
    int valuesCount = 0;
    bool correctCount = true;

    std::string token = root->getNextToken(arguments, argumentIdx);

    while (!token.empty())
    {
        if (!root->isNumber(token))
        {
            correctCount = false;
        }
        else
        {
            if (valuesCount < count)
            {
                values[valuesCount] = root->stringToNumber(token);
            }

            valuesCount++;
        }

        token = root->getNextToken(arguments, argumentIdx);
    }

    if (valuesCount != count)
    {
        correctCount = false;
    }

    if (correctCount)
    {
        result = root->calculate(variables, values, count, divisionByZero);
    }

    if (values != NULL)
    {
        delete[] values;
    }

    if (variables != NULL)
    {
        delete[] variables;
    }

    return correctCount;
}

bool CTree::getWasCharacterIgnored() const
{
    return wasCharacterIgnored;
}

bool CTree::getWasArgumentAdded() const
{
    return wasArgumentAdded;
}

bool CTree::getWereExtraArgumentsIgnored() const
{
    return wereExtraArgumentsIgnored;
}

bool CTree::getWasInvalidTokenIgnored() const
{
    return wasInvalidTokenIgnored;
}